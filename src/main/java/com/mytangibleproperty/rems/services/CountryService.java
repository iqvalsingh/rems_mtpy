/**
 * 
 */
package com.mytangibleproperty.rems.services;

import java.util.List;

import com.mytangibleproperty.rems.bo.Country;

/**
 * @author Satbir Kaur
 *
 */
public interface CountryService
{
	public List<Country> getAllCountry();

	public Country getCountryById(int countryId);

	public void updateCountry(Country country);

	public void saveCountry(Country country);
}
