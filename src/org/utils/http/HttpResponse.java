package org.utils.http;






import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;



/* *
 *类名：HttpResponse
 *功能：Http返回对象的封�?
 *详细：封装Http返回信息
 *版本�?.3
 *日期�?011-08-17
 *说明�?
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的�?��，按照技术文档编�?并非�?��要使用该代码�?
 *该代码仅供学习和研究支付宝接口使用，只是提供�?��参�?�?
 */

public class HttpResponse {

    /**
     * 返回中的Header信息
     */
    private Header[] responseHeaders;

    /**
     * String类型的result
     */
    private String   stringResult;

    /**
     * btye类型的result
     */
    private byte[]   byteResult;

    public Header[] getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Header[] responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public byte[] getByteResult() {
        if (byteResult != null) {
            return byteResult;
        }
        if (stringResult != null) {
            return stringResult.getBytes();
        }
        return null;
    }

    public void setByteResult(byte[] byteResult) {
        this.byteResult = byteResult;
    }

    public String getStringResult() throws UnsupportedEncodingException {
        if (stringResult != null) {
            return stringResult;
        }
        if (byteResult != null) {
            return new String(byteResult,"utf-8");
        }
        return null;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
    }

}
