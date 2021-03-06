package com.tsubaki.dm.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tsubaki.dm.model.VvsBean;

@Repository("VvsDaoJdbcImpl")
public class VvsDaoJdbcImpl implements VvsDao {

    @Autowired
    JdbcTemplate jdbc;

    // m_vvsのusedAttribute毎のデータを取得.
    @Override
    public List<VvsBean> selectAttribute(String usedAttribute) {

        //RowMapperの生成
        RowMapper<VvsBean> rowMapper = new VvsRowMapper();

        // m_vvsから指定された属性のデータを全件取得
        return jdbc.query("SELECT * FROM m_vvs where used_attribute= ? ORDER BY sort_key",rowMapper,usedAttribute);

    }

    /**
     * vvsマスタの件数を取得する
     */
    @Override
    public int count() throws DataAccessException {

        // 全件取得してカウント
        int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_vvs", Integer.class);

        return count;
    }

    /**
     * usedAttribute毎のｖｖｓマスタの件数を取得する
     */
    @Override
    public int count(String usedAttribute) throws DataAccessException {
    	
    	// 全件取得してカウント
    	int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_vvs where used_attribute = ?", Integer.class, usedAttribute);
    	
    	return count;
    }
    
    @Override
    public VvsBean selectOne(int id) throws DataAccessException {
    	
    	// 1件取得用SQL
    	String sql = "SELECT * FROM m_vvs where vvs_id = ?";
    	
    	// RowMapperの生成
    	VvsRowMapper vvsRowMapper = new VvsRowMapper();
    	
    	// 全件取得してカウント
    	return jdbc.queryForObject(sql, vvsRowMapper, id);
    	
    }
    
    // m_vvsテーブルのレコードを１件更新.
    @Override
    public int updateOne(VvsBean vvsBean) throws DataAccessException {

    	// 更新用SQL
    	String sql ="UPDATE m_vvs SET sort_key = ?, key_str = ?, value_str = ? where vvs_id = ?";
        //１件更新
        int rowNumber = jdbc.update(sql,vvsBean.getSortKey(), vvsBean.getKey(), vvsBean.getValue(), vvsBean.getVvsId());

        return rowNumber;
    }

    // m_vvsのレコードを一件登録
    @Override
    public int insertOne(VvsBean vvsBean) throws DataAccessException {
    	
    	// 登録用SQL
    	String sql ="INSERT INTO m_vvs(vvs_id, used_attribute, sort_key, key_str, value_str)"
    			+ " VALUES (?, ?, ?, ?, ?)";
    	// １件登録
    	int rowNumber = jdbc.update(sql, vvsBean.getVvsId(), vvsBean.getUsedAttribute(), vvsBean.getSortKey(), vvsBean.getKey(), vvsBean.getValue());
    	
    	return rowNumber;
    }
    
    // m_vvsのレコードを１件削除.
    @Override
    public int deleteOne(int vvsId) throws DataAccessException {
    	
        //１件削除
        int rowNumber = jdbc.update("DELETE FROM m_vvs WHERE vvs_id = ?", vvsId);

        return rowNumber;
    }
    
    
    // デバイスIDの最大値を取得
    @Override
    public int getMaxNo(){
    	int nextId = 0;
    	int vvsId = jdbc.queryForObject("SELECT MAX(vvs_id) FROM m_vvs", Integer.class);
    	nextId = vvsId + 1;
    	return nextId;
    }

}
