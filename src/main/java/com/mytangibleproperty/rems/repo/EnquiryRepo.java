/**
 * 
 */
package com.mytangibleproperty.rems.repo;

import java.util.List;
import java.util.Map;

import com.mytangibleproperty.rems.bo.Enquiry;

/**
 * @author Satbir Kaur
 *
 */
public interface EnquiryRepo extends CommonRepo
{
	public List<Enquiry> getEnquiryByCriteria(Map<String, Object> criteria);

}
