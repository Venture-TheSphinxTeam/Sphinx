package helpers.auth;

import models.User;

public class DefaultAuth implements AuthValidation{

	@Override
	public String validateUser(String username, String password) {
		User user = User.findByName(username);

        if(user == null){
            return "This user does not exist in the system";
        }

        if (!user.getPassword().equals(password)) {
        	
            return "Invalid password";
        }
        return null;
    }

}
