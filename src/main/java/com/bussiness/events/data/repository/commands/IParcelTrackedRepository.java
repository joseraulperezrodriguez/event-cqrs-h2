package com.bussiness.events.data.repository.commands;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bussiness.events.domain.commands.ParcelTracked;

public interface IParcelTrackedRepository extends CrudRepository<ParcelTracked, String>, PagingAndSortingRepository<ParcelTracked, String> {

}
