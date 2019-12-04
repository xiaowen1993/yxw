package com.alipay;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 当面付API
 * 
 */
public class AlipayQrTradePay {
    
    private static Logger logger = Logger.getLogger(AlipayQrTradePay.class);
    
    /**
     * 获取支付宝接口调用客户端
     * 
     * @param appId
     * @param privateKey
     *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
     * @return
     */
    private static AlipayClient getAlipayClient(String appId, String privateKey) {
        return AlipayAPIClientFactory.getAlipayClient(appId, privateKey);
    }
    
    /***
     * 
     * 统一收单交易撤销接口
     * 
     * @param appId
     * @param privateKey
     * @param outTradeNo
     *            订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     * @param tradeNo
     *            支付宝交易号，和商户订单号不能同时为空
     * @return
     */
    public static AlipayTradeCancelResponse tradeCancel(String appId, String privateKey, String outTradeNo,
            String tradeNo) {
        JSONObject bizContentJson = new JSONObject();
        // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
        bizContentJson.put("out_trade_no", outTradeNo);
        // 支付宝交易号，和商户订单号不能同时为空
        bizContentJson.put("trade_no", tradeNo);
        
        AlipayTradeCancelResponse response = null;
        try {
            AlipayClient alipayClient = getAlipayClient(appId, privateKey);
            AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
            request.setBizContent(bizContentJson.toJSONString());
            logger.info("AlipayTradeCancelRequest request.getTextParams():" + request.getTextParams().toString());
            response = alipayClient.execute(request);
            logger.info("AlipayTradeCancelResponse:" + JSONObject.toJSONString(response));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.error("AlipayTradeCancelService——>tradeCancel方法出现异常");
        }
        return response;
    }
    
    /**
     * 统一收单交易退款接口
     * 
     * @param appId
     * @param privateKey
     * @param outTradeNo
     *            订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     * @param tradeNo
     *            支付宝交易号，和商户订单号不能同时为空
     * @param refundAmount
     *            需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @param refundReason
     *            退款的原因说明
     * @param outRequestNo
     *            标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     * @param operatorId
     *            商户的操作员编号
     * @param terminalId
     *            商户的终端编号
     * @return
     */
    public static AlipayTradeRefundResponse tradeRefund(String appId, String privateKey, String outTradeNo,
            String tradeNo, String refundAmount, String refundReason, String outRequestNo, String operatorId,
            String storeId, String terminalId) {
        JSONObject bizContentJson = new JSONObject();
        // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
        bizContentJson.put("out_trade_no", outTradeNo);
        // 支付宝交易号，和商户订单号不能同时为空
        bizContentJson.put("trade_no", tradeNo);
        // 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
        bizContentJson.put("refund_amount", refundAmount);
        // 退款的原因说明
        bizContentJson.put("refund_reason", refundReason);
        // 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        bizContentJson.put("out_request_no", outRequestNo);
        // 商户的操作员编号
        bizContentJson.put("operator_id", operatorId);
        // 商户的门店编号
        bizContentJson.put("store_id", storeId);
        // 商户的终端编号
        bizContentJson.put("terminal_id", terminalId);
        AlipayTradeRefundResponse response = null;
        try {
            AlipayClient alipayClient = getAlipayClient(appId, privateKey);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent(bizContentJson.toJSONString());
            logger.info("AlipayTradeRefundRequest request.getTextParams():" + request.getTextParams().toString());
            response = alipayClient.execute(request);
            logger.info("AlipayTradeRefundResponse:" + JSONObject.toJSONString(response));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.error("AlipayTradeRefundService——>tradeRefund方法出现异常");
            
        }
        return response;
    }
    
    /**
     * 统一收单线下交易查询接口
     * 
     * @param appId
     * @param privateKey
     * @param outTradeNo
     *            订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     * @param tradeNo
     *            支付宝交易号，和商户订单号不能同时为空
     * @return
     */
    public static AlipayTradeQueryResponse tradeQuery(String appId, String privateKey, String outTradeNo, String tradeNo) {
        JSONObject bizContentJson = new JSONObject();
        // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
        bizContentJson.put("out_trade_no", outTradeNo);
        // 支付宝交易号，和商户订单号不能同时为空
        bizContentJson.put("trade_no", tradeNo);
        
        AlipayTradeQueryResponse response = null;
        
        try {
            AlipayClient alipayClient = getAlipayClient(appId, privateKey);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent(bizContentJson.toJSONString());
            logger.info("AlipayTradeQueryRequest request.getTextParams():" + request.getTextParams().toString());
            response = alipayClient.execute(request);
            logger.info("AlipayTradeQueryResponse:" + JSONObject.toJSONString(response));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.error("AlipayTradeQueryService——>tradeQuery方法出现异常");
        }
        return response;
    }
    
    /**
     * 条码支付 统一收单交易支付接口
     * 
     * @param appId
     * @param privateKey
     * @param outTradeNo
     *            商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复 此参数为his订单号
     * @param authCode
     *            支付授权码
     * @param subject
     *            订单标题
     * @param totalAmount
     *            订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。 如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入； //
     *            如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】
     * @param discountableAmount
     *            参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。 如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
     * @param undiscountableAmount
     *            不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。如果该值未传入，但传入了【订单总金额】和【可打折金额】，则该值默认为【订单总金额】-【可打折金额】
     * @param body
     *            订单描述
     * @param goodsDetailJsonArray
     *            订单包含的商品列表信息，Json格式，其它说明详见商品明细说明
     * @param operatorId
     *            商户操作员编号
     * @param storeId
     *            商户门店编号
     * @param terminalId
     *            业务扩展参数
     * @param extendParams
     *            业务扩展参数
     * @param timeOutExpress
     *            该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如
     *            1.5h，可转换为90m
     * @return
     */
    
    public static AlipayTradePayResponse tradePay(String appId, String privateKey, String outTradeNo, String authCode,
            String subject, String totalAmount, String discountableAmount, String undiscountableAmount, String body,
            JSONArray goodsDetailJsonArray, String operatorId, String storeId, String terminalId,
            JSONObject extendParams, String timeOutExpress) {
        JSONObject bizContentJson = new JSONObject();
        // 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复 此参数为his订单号
        bizContentJson.put("out_trade_no", outTradeNo);
        // 支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code
        bizContentJson.put("scene", "bar_code");
        // 支付授权码
        bizContentJson.put("auth_code", authCode);
        // 订单标题
        bizContentJson.put("subject", subject);
        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。 如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入；
        // 如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】
        bizContentJson.put("total_amount", totalAmount);
        // 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。 如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
        bizContentJson.put("discountable_amount", discountableAmount);
        // 不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。如果该值未传入，但传入了【订单总金额】和【可打折金额】，则该值默认为【订单总金额】-【可打折金额】
        bizContentJson.put("undiscountable_amount", undiscountableAmount);
        // 订单描述
        bizContentJson.put("body", body);
        // 订单包含的商品列表信息，Json格式，其它说明详见商品明细说明
        bizContentJson.put("goods_detail", goodsDetailJsonArray);
        // 商户操作员编号
        bizContentJson.put("operator_id", operatorId);
        // 商户门店编号
        bizContentJson.put("store_id", storeId);
        // 商户机具终端编号
        bizContentJson.put("terminal_id", terminalId);
        // 业务扩展参数
        bizContentJson.put("extend_params", extendParams);
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为
        // 90m
        bizContentJson.put("timeout_express", timeOutExpress);
        AlipayTradePayResponse response = null;
        try {
            
            AlipayClient alipayClient = getAlipayClient(appId, privateKey);
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.setBizContent(bizContentJson.toJSONString());
            logger.info("AlipayTradePayRequest request.getTextParams():" + request.getTextParams().toString());
            
            response = alipayClient.execute(request);
            logger.info("AlipayTradePayResponse:" + JSONObject.toJSONString(response));
            // 这里只是简单的打印，请开发者根据实际情况自行进行处理
            
        } catch (AlipayApiException e) {
            e.printStackTrace();
            logger.error("AlipayTradePayService——>tradePay方法出现异常");
            
        }
        
        return response;
        
    }
}
