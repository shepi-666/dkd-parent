package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Channel;
import com.dkd.manage.domain.vo.ChannelVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 售货机货道Mapper接口
 * 
 * @author javadong
 * @date 2024-08-15
 */
public interface ChannelMapper 
{
    /**
     * 查询售货机货道
     * 
     * @param id 售货机货道主键
     * @return 售货机货道
     */
    public Channel selectChannelById(Long id);

    /**
     * 查询售货机货道列表
     * 
     * @param channel 售货机货道
     * @return 售货机货道集合
     */
    public List<Channel> selectChannelList(Channel channel);

    /**
     * 新增售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    public int insertChannel(Channel channel);

    /**
     * 修改售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    public int updateChannel(Channel channel);


    /**
     * 批量修改渠道
     * @param list
     * @return
     */
    public int batchUpdateChannels(List<Channel> list);

    /**
     * 删除售货机货道
     * 
     * @param id 售货机货道主键
     * @return 结果
     */
    public int deleteChannelById(Long id);

    /**
     * 批量删除售货机货道
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChannelByIds(Long[] ids);

    /**
     * 实现批量插入货道
     */
    public int batchInsertChannels(List<Channel> channelList);


    /**
     * 根据skuId查询货道
     */
    public int countChannelBySkuIds(Long[] skuIds);

    /**
     * 根据售货机编号查询货道列表
     * @param innerCode
     * @return List<ChannelVo>
     */
    public List<ChannelVo> selectChannelVoByInnerCode(String innerCode);


    /**
     * 根据售货机编号和货道编号查询货道信息
     * @param innerCode
     * @param ChannelCode
     * @return
     */
    @Select("select * from tb_channel where inner_code = #{innerCode} and  channel_code = #{channelCode}")
    Channel getChannelInfo(@Param("innerCode") String innerCode, @Param("channelCode")String channelCode);

}
