package com.heinsd.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

import com.heinsd.dao.DAOImpl;
import com.heinsd.model.User;

/*
 * Password callback intercepter to set expected password for verification.  
 */
public class PasswordCallbackHandler implements CallbackHandler {

	Map<String, String> passwords = new HashMap<>();

	public PasswordCallbackHandler() {

	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback c : callbacks) {
			if (c instanceof WSPasswordCallback) {
				WSPasswordCallback pc = (WSPasswordCallback) c;

				String identifier = pc.getIdentifier();
				User u = DAOImpl.getInstance().getUser(identifier);
				pc.setPassword(u.getPass());

				return;

			}
		}
	}

}
