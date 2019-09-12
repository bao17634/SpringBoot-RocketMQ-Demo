package com.byr.project.orderdemo.mapper;

import com.byr.project.orderdemo.entity.TssHouse;
import com.byr.project.orderdemo.entity.TssHouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TssHouseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    long countByExample(TssHouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int deleteByExample(TssHouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int insert(TssHouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int insertSelective(TssHouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    List<TssHouse> selectByExample(TssHouseExample example);
    Integer selectNumber(@Param("code") String code);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    TssHouse selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TssHouse record, @Param("example") TssHouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TssHouse record, @Param("example") TssHouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TssHouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.tss_house
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TssHouse record);
}