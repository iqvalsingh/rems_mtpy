/**
 * 
 */
package com.mytangibleproperty.rems.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytangibleproperty.rems.bo.Country;
import com.mytangibleproperty.rems.repo.CommonRepo;
import com.mytangibleproperty.rems.services.CountryService;

/**
 * @author Satbir Kaur
 *
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService
{
	@Autowired
	@Qualifier("commonRepoImpl")
	CommonRepo commonRepo;

	@Override
	public List<Country> getAllCountry()
	{
		return commonRepo.getAll(Country.class);
	}

	@Override
	public Country getCountryById(int countryId)
	{
		return commonRepo.getById(Country.class, countryId);
	}

	@Override
	public void updateCountry(Country country)
	{
		commonRepo.update(country);
	}

	@Override
	public void saveCountry(Country country)
	{
		commonRepo.create(country);
	}

}
