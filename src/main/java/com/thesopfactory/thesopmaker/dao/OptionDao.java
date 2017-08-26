package com.thesopfactory.thesopmaker.dao;

import java.util.List;

import com.thesopfactory.thesopmaker.model.Option;

/**
Author- Nikhil
Date - Jul 30, 2017
*/
public interface OptionDao {

	List<Option> getOptionsByQuestion( long questionId );
}
