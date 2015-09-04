package com.aomei.dao;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


@Slf4j
public class BaseDao { 
	protected int update(String sqlID, Object obj, SqlSession sqlSession) {
		int result = sqlSession.update(sqlID, obj);
		log.debug("update result rows = {}", result);
		return result;
	}

	protected int delete(String sqlID, Object obj, SqlSession sqlSession) {
		int result = sqlSession.delete(sqlID, obj);
		log.debug("delete result rows = {}", result);
		return result;
	}

	protected <T> List<T> selectList(String sqlID, Object obj, SqlSession sqlSession) {
		List<T> result = sqlSession.selectList(sqlID, obj);
		if (result == null || result.isEmpty()) {
			log.debug("select result is empty");
		} else {
			log.debug("select result rows = {}", result.size());
		}
		return result;
	}

	protected <T> List<T> selectList(String sqlID, SqlSession sqlSession) {
		List<T> result = sqlSession.selectList(sqlID);
		if (result == null || result.isEmpty()) {
			log.debug("select result is empty");
		} else {
			log.debug("select result rows = {}", result.size());
		}
		return result;
	}

	protected <T> T selectOne(String sqlID, Object obj, SqlSession sqlSession) {
		T result = (T) sqlSession.selectOne(sqlID, obj);
		if (result == null) {
			log.debug("select result rows = 0");
		} else {
			log.debug("select result rows = 1");
		}
		return result;
	}

	protected <T> T selectOne(String sqlID, SqlSession sqlSession) {
		T result = (T) sqlSession.selectOne(sqlID);
		if (result == null) {
			log.debug("select result rows = 0");
		} else {
			log.debug("select result rows = 1");
		}
		return result;
	}

	protected int insert(String sqlID, Object obj, SqlSession sqlSession) {
		int result = sqlSession.insert(sqlID, obj);
		log.debug("insert result rows = {}", result);
		return result;
	}
}

