/**
 * 
 */
package com.mytangibleproperty.rems.services;

import com.mytangibleproperty.rems.bo.Page;

/**
 * @author Satbir Kaur
 *
 */
public interface PageService
{
	public Page getPageByPageType(String pageType);

	public Page getPageById(int pageId);

	public void updatePage(Page page);

	public void flush();
}
