/**
 * 
 */
package com.mytangibleproperty.rems.repo;

import java.util.List;
import java.util.Map;

import com.mytangibleproperty.rems.bo.User;

/**
 * @author Satbir Kaur
 *
 */
public interface UserRepo extends CommonRepo
{
	public List<User> getUsers(Map<String, Object> criteria);

	public Map<String, Integer> getUserCountByType();

}
