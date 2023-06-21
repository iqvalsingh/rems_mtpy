/**
 * 
 */
package com.mytangibleproperty.rems.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytangibleproperty.rems.bo.Feedback;
import com.mytangibleproperty.rems.repo.FeedbackRepo;
import com.mytangibleproperty.rems.services.FeedbackService;

/**
 * @author Satbir Kaur
 *
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService
{
	@Autowired
	FeedbackRepo feedbackRepo;

	@Override
	public List<Feedback> getFeedbackByPublishType(int publishType)
	{
		return feedbackRepo.getFeedbackByPublishType(publishType);
	}

	@Override
	public Feedback getFeedbackById(int feedbackId)
	{
		return feedbackRepo.getById(Feedback.class, feedbackId);
	}

	@Override
	public void updateFeedback(Feedback feedback)
	{
		feedbackRepo.update(feedback);
	}

	@Override
	public void saveFeedback(Feedback feedback)
	{
		feedbackRepo.create(feedback);
	}

	@Override
	public void flush()
	{
		feedbackRepo.flush();
	}

	@Override
	public void deleteFeedbackById(int feedbackId)
	{
		feedbackRepo.deleteById(Feedback.class, feedbackId);
	}

	@Override
	public List<Feedback> getFeedbackByPropertyId(String propertyId)
	{
		return feedbackRepo.getFeedbackByPropertyId(propertyId);
	}

}
