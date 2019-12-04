<#assign oscache=JspTaglibs["/WEB-INF/tlds/oscache.tld"]/>
<@oscache.cache  time=0  scope="application">
<input type="hidden" id="basePath" value="${basePath}">
<script type="text/javascript" src="${basePath}js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${basePath}js/jquery.json.min.js"></script>
</@oscache.cache>