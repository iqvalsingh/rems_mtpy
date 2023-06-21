/**
 * 
 */
package com.mytangibleproperty.rems.services;

import java.util.List;

import com.mytangibleproperty.rems.bo.PropertyType;

/**
 * @author Satbir Kaur
 *
 */
public interface PropertyTypeService
{
	public List<PropertyType> getAllPropertyType();

	public PropertyType getPropertyTypeById(int propertyTypeId);

	public void updatePropertyType(PropertyType propertyType);

	public void savePropertyType(PropertyType propertyType);

}
