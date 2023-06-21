/**
 * 
 */
package com.mytangibleproperty.rems.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytangibleproperty.rems.bo.Page;
import com.mytangibleproperty.rems.repo.PageRepo;
import com.mytangibleproperty.rems.services.PageService;

/**
 * @author Satbir Kaur
 *
 */
@Service
@Transactional
public class PageServiceImpl implements PageService
{
	@Autowired
	PageRepo pageRepo;

	@Override
	public Page getPageByPageType(String pageType)
	{
		return pageRepo.getPageByPageType(pageType);
	}

	@Override
	public Page getPageById(int pageId)
	{
		return pageRepo.getById(Page.class, pageId);
	}

	@Override
	public void updatePage(Page page)
	{
		pageRepo.update(page);
	}

	@Override
	public void flush()
	{
		pageRepo.flush();
	}

}
