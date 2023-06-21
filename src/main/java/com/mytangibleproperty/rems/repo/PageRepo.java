/**
 * 
 */
package com.mytangibleproperty.rems.repo;

import com.mytangibleproperty.rems.bo.Page;

/**
 * @author Satbir Kaur
 *
 */
public interface PageRepo extends CommonRepo
{

	public Page getPageByPageType(String pageType);
}
