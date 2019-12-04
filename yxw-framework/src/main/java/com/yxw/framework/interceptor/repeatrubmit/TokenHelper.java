/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.yxw.framework.interceptor.repeatrubmit;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * @Package: com.yxw.framework.interceptor.submission
 * @ClassName: TokenHelper
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-19
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 * 参考struts2 的TokenHelper
 */
public class TokenHelper {

	/**
	 * The default namespace for storing token session values
	 */
	public static final String TOKEN_NAMESPACE = "yxw.tokens";

	/**
	 * The default name to map the token value
	 */
	public static final String DEFAULT_TOKEN_NAME = "token";

	/**
	 * The name of the field which will hold the token name
	 */
	public static final String TOKEN_NAME_FIELD = "yxw.token.name";
	private static final Logger logger = LoggerFactory.getLogger(TokenHelper.class);
	private static final Random RANDOM = new SecureRandom();

	/**
	 * Sets a transaction token into the session using the default token name.
	 *
	 * @return the token string
	 */
	public static String setToken(HttpServletRequest request) {
		return setToken(DEFAULT_TOKEN_NAME, request);
	}

	/**
	 * Sets a transaction token into the session based on the provided token name.
	 *
	 * @param tokenName the token name based on which a generated token value is stored into session; for actual session
	 *                  store, this name will be prefixed by a namespace.
	 *
	 * @return the token string
	 */
	public static String setToken(String tokenName, HttpServletRequest request) {
		String token = generateGUID();
		setSessionToken(tokenName, token, request);
		return token;
	}

	/**
	 * Put a given named token into the session map. The token will be stored with a namespace prefix prepended.
	 *
	 * @param tokenName the token name based on which given token value is stored into session; for actual session store,
	 *                  this name will be prefixed by a namespace.
	 * @param token     the token value to store
	 */
	public static void setSessionToken(String tokenName, String token, HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			logger.info("session tokenName : {} , token :{}", new Object[] { buildTokenSessionAttributeName(tokenName), token });
			session.setAttribute(buildTokenSessionAttributeName(tokenName), token);
			request.setAttribute(DEFAULT_TOKEN_NAME, token);
		} catch (IllegalStateException e) {
			// WW-1182 explain to user what the problem is
			String msg =
					"Error creating HttpSession due response is commited to client. You can use the CreateSessionInterceptor or create the HttpSession from your action before the result is rendered to the client: "
							+ e.getMessage();
			if (logger.isErrorEnabled()) {
				logger.error(msg, e);
			}
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Build a name-spaced token session attribute name based on the given token name.
	 *
	 * @param tokenName the token name to prefix
	 *
	 * @return the name space prefixed session token name
	 */
	public static String buildTokenSessionAttributeName(String tokenName) {
		return TOKEN_NAMESPACE + "." + tokenName;
	}

	/**
	 * Gets a transaction token from the params in the ServletActionContext using the default token name.
	 *
	 * @return token
	 */
	public static String getToken(HttpServletRequest request) {
		return getToken(DEFAULT_TOKEN_NAME, request);
	}

	/**
	 * Gets the Token value from the params in the ServletActionContext using the given name
	 *
	 * @param tokenName the name of the parameter which holds the token value
	 * @return the token String or null, if the token could not be found
	 */
	public static String getToken(String tokenName, HttpServletRequest request) {
		if (tokenName == null) {
			return null;
		}

		Map params = request.getParameterMap();
		logger.info(" TokenHelper  params:{}", JSON.toJSONString(params));

		String[] tokens = (String[]) params.get(tokenName);

		String token;
		if ( ( tokens == null ) || ( tokens.length < 1 )) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not find token mapped to tokenName:{} " + tokenName);
			}

			return null;
		}
		token = tokens[0];
		logger.info(" TokenHelper  requestToken:{}", token);
		return token;
	}

	/**
	 * Gets the token name from the Parameters in the ServletActionContext
	 *
	 * @return the token name found in the params, or null if it could not be found
	 */
	public static String getTokenName(HttpServletRequest request) {
		Map params = request.getParameterMap();

		if (!params.containsKey(TOKEN_NAME_FIELD)) {
			if (logger.isWarnEnabled()) {
				logger.warn("Could not find token name in params.");
			}

			return null;
		}

		String[] tokenNames = (String[]) params.get(TOKEN_NAME_FIELD);
		String tokenName;

		if ( ( tokenNames == null ) || ( tokenNames.length < 1 )) {
			if (logger.isWarnEnabled()) {
				logger.warn("Got a null or empty token name.");
			}

			return null;
		}

		tokenName = tokenNames[0];

		return tokenName;
	}

	/**
	 * Checks for a valid transaction token in the current request params. If a valid token is found, it is
	 * removed so the it is not valid again.
	 *
	 * @return false if there was no token set into the params (check by looking for {@link #TOKEN_NAME_FIELD}), true if a valid token is found
	 */
	public static boolean validToken(HttpServletRequest request) {
		/*
		String tokenName = getTokenName(request);

		if (tokenName == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("no token name found -> Invalid token ");
			}
			return false;
		}
		*/
		String token = getToken(DEFAULT_TOKEN_NAME, request);

		if (token == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("no token found for token name :{} -> Invalid token ", DEFAULT_TOKEN_NAME);
			}
			return false;
		}

		HttpSession session = request.getSession();
		String tokenSessionName = buildTokenSessionAttributeName(DEFAULT_TOKEN_NAME);
		String sessionToken = (String) session.getAttribute(tokenSessionName);

		if (!token.equals(sessionToken)) {
			if (logger.isWarnEnabled()) {
				logger.warn("reqestToken:{} does not match the sessionToken:{}.", new Object[] { token, sessionToken });
			}
			try {
				//重复提交睡眠60秒  防止重复提交比正常提交先返回
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		// remove the token so it won't be used again
		session.removeAttribute(tokenSessionName);

		return true;
	}

	public static String generateGUID() {
		return new BigInteger(165, RANDOM).toString(36).toUpperCase();
	}
}
