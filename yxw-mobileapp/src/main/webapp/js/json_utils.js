/**
 * json 工具包
 */
var $json = {};
var _escapeable = /["\\\x00-\x1f\x7f-\x9f]/g;
var _meta = {
  '\b' : '\\b',
  '\t' : '\\t',
  '\n' : '\\n',
  '\f' : '\\f',
  '\r' : '\\r',
  '"' : '\\"',
  '\\' : '\\\\'
};

/**
 * 把JSON对象转成JSON字符串
 */
$json.toJSONString = function(o) {
  if (typeof (JSON) == 'object' && JSON.stringify)
    return JSON.stringify(o);
  var type = typeof (o);
  if (o === null)
    return "null";
  if (type == "undefined")
    return undefined;
  if (type == "number" || type == "boolean")
    return o + "";
  if (type == "string")
    return $json.quoteString(o);
  if (type == 'object') {
    if (typeof o.toJSON == "function")
      return $json.toJSONString(o.toJSON());
    if (o.constructor === Date) {
      var month = o.getUTCMonth() + 1;
      if (month < 10)
        month = '0' + month;
      var day = o.getUTCDate();
      if (day < 10)
        day = '0' + day;
      var year = o.getUTCFullYear();
      var hours = o.getUTCHours();
      if (hours < 10)
        hours = '0' + hours;
      var minutes = o.getUTCMinutes();
      if (minutes < 10)
        minutes = '0' + minutes;
      var seconds = o.getUTCSeconds();
      if (seconds < 10)
        seconds = '0' + seconds;
      var milli = o.getUTCMilliseconds();
      if (milli < 100)
        milli = '0' + milli;
      if (milli < 10)
        milli = '0' + milli;
      return '"' + year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds + '.' + milli + '"';
    }
    if (o.constructor === Array) {
      var ret = [];
      for (var i = 0; i < o.length; i++)
        ret.push($json.toJSONString(o[i]) || "null");
      return "[" + ret.join(",") + "]";
    }
    var pairs = [];
    for ( var k in o) {
      var name;
      var type = typeof k;
      if (type == "number")
        name = '"' + k + '"';
      else if (type == "string")
        name = $json.quoteString(k);
      else
        continue;
      if (typeof o[k] == "function")
        continue;
      var val = $json.toJSONString(o[k]);
      pairs.push(name + ":" + val);
    }
    return "{" + pairs.join(", ") + "}";
  }
};

$json.quoteString = function(string) {
  if (string.match(_escapeable)) {
      return '"' + string.replace(_escapeable, function(a) {
          var c = _meta[a];
          if (typeof c === 'string')
              return c;
          c = a.charCodeAt();
          return '\\u00' + Math.floor(c / 16).toString(16)
                  + (c % 16).toString(16);
      }) + '"';
  }
  return '"' + string + '"';
};

/**
 * 把JSON字符串转成JSON对象
 */
$json.toJSONObject = function(src) {
  if (typeof (JSON) == 'object' && JSON.parse)
    return JSON.parse(src);
  return eval("(" + src + ")");
};