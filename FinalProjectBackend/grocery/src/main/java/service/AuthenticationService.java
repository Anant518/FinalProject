package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import config.MessageStrings;
import entity.AuthenticationToken;
import entity.User;
import exception.AuthenticationFailException;
import repository.TokenRepository;
import utils.Helper;

@Service
public class AuthenticationService {
	
	@Autowired
	TokenRepository repository;
	
	
	public void saveConformationToken(AuthenticationToken authenticationToken) {
		
		repository.save(authenticationToken);
	}

	public AuthenticationToken getToken(User user) {
		return repository.findTokenByUser(user);
	}
	
	public User getUser(String token) {
		AuthenticationToken at = repository.findTokenByToken(token);
		if(Helper.notNull(at)) {
			if(Helper.notNull(at.getUser())) {
				return at.getUser();
			}
		}
			
		return null;
	}
	
	
	 public void authenticate(String token) throws AuthenticationFailException {
	        if (!Helper.notNull(token)) {
	            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_PRESENT);
	        }
	        if (!Helper.notNull(getUser(token))) {
	            throw new AuthenticationFailException(MessageStrings.AUTH_TOEKN_NOT_VALID);
	        }
	    }
	
	
}
