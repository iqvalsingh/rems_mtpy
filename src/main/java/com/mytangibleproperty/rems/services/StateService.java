/**
 * 
 */
package com.mytangibleproperty.rems.services;

import java.util.List;

import com.mytangibleproperty.rems.bo.State;

/**
 * @author Satbir Kaur
 *
 */
public interface StateService
{
	public List<State> getAllState();

	public State getStateById(int stateId);

	public void updateState(State state);

	public void saveState(State state);

	public void flush();
}
