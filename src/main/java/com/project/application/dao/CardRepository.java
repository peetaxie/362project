package com.project.application.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.application.model.Card;

/** 
* @author Yuhang Xie 
* @version April 20, 2019 6:15:30 PM 
*/

@Repository
public interface CardRepository extends CrudRepository<Card,Long>{
	Card findById(Integer id);
}
