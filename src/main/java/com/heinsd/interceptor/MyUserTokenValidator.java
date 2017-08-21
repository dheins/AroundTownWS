package com.heinsd.interceptor;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.ext.WSSecurityException.ErrorCode;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;

import com.heinsd.dao.DAO;
import com.heinsd.dao.DAOImpl;
import com.heinsd.model.User;

import utils.Crypto;
import utils.HashPass;

/*
 *  Intercepter to decrypt and hash incoming password so it can be verified against the stored hash value
 */
public class MyUserTokenValidator extends UsernameTokenValidator {

	@Override
	protected void verifyPlaintextPassword(org.apache.wss4j.dom.message.token.UsernameToken usernameToken,
			org.apache.wss4j.dom.handler.RequestData data) throws org.apache.wss4j.common.ext.WSSecurityException {
		
		String password = usernameToken.getPassword();
		
		String decrypt = Crypto.decrypt(password.getBytes());
		DAO dao = DAOImpl.getInstance();
		User user = dao.getUser(usernameToken.getName());

		String actual = HashPass.hash(decrypt, user.getSalt());
		String expected = user.getPass();
		if (!actual.equals(expected)) {
			throw new WSSecurityException(ErrorCode.FAILED_AUTHENTICATION,
					WSSecurityException.FAILED_AUTHENTICATION_ERR);
		}
		usernameToken.setPassword(actual);
		super.verifyPlaintextPassword(usernameToken, data);

	}

}
