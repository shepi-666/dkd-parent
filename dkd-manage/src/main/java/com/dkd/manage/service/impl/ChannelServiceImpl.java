package com.dkd.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.dto.ChannelConfigDto;
import com.dkd.manage.domain.vo.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.ChannelMapper;
import com.dkd.manage.domain.Channel;
import com.dkd.manage.service.IChannelService;

/**
 * 售货机货道Service业务层处理
 * 
 * @author javadong
 * @date 2024-08-15
 */
@Service
public class ChannelServiceImpl implements IChannelService 
{
    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 查询售货机货道
     * 
     * @param id 售货机货道主键
     * @return 售货机货道
     */
    @Override
    public Channel selectChannelById(Long id)
    {
        return channelMapper.selectChannelById(id);
    }

    /**
     * 查询售货机货道列表
     * 
     * @param channel 售货机货道
     * @return 售货机货道
     */
    @Override
    public List<Channel> selectChannelList(Channel channel)
    {
        return channelMapper.selectChannelList(channel);
    }

    /**
     * 新增售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    @Override
    public int insertChannel(Channel channel)
    {
        channel.setCreateTime(DateUtils.getNowDate());
        return channelMapper.insertChannel(channel);
    }

    /**
     * 修改售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    @Override
    public int updateChannel(Channel channel)
    {
        channel.setUpdateTime(DateUtils.getNowDate());
        return channelMapper.updateChannel(channel);
    }

    /**
     * 批量删除售货机货道
     * 
     * @param ids 需要删除的售货机货道主键
     * @return 结果
     */
    @Override
    public int deleteChannelByIds(Long[] ids)
    {
        return channelMapper.deleteChannelByIds(ids);
    }

    /**
     * 删除售货机货道信息
     * 
     * @param id 售货机货道主键
     * @return 结果
     */
    @Override
    public int deleteChannelById(Long id)
    {
        return channelMapper.deleteChannelById(id);
    }


    /**
     * 批量新增售货机货道
     * @param channelList
     * @return
     */
    @Override
    public int batchInsertChannels(List<Channel> channelList) {
        return channelMapper.batchInsertChannels(channelList);
    }


    /**
     * 根据商品ID集合统计货道数量
     * @param skuIds
     * @return 统计结果
     */
    @Override
    public int countChannelBySkuIds(Long[] skuIds) {

        return channelMapper.countChannelBySkuIds(skuIds);
    }

    /**
     * 货道关联商品
     * @param channelConfigDto
     * @return
     */
    @Override
    public int setChannel(ChannelConfigDto channelConfigDto) {
        // 将Dto转换为Po对象
        List<Channel> channelList = channelConfigDto.getChannelList().stream().map(channelSkuDto -> {
            // 根据售货机编号和货道编号查询货道信息
            Channel channel = channelMapper.getChannelInfo(channelSkuDto.getInnerCode(), channelSkuDto.getChannelCode());
            if (channel != null) {

                // 关联商品信息
                channel.setSkuId(channelSkuDto.getSkuId());
                channel.setUpdateTime(DateUtils.getNowDate());
                channelMapper.updateChannel(channel);
            }
            return channel;
        }).collect(Collectors.toList());


        // 批量修改货道对象
        return channelMapper.batchUpdateChannels(channelList);
    }

    /**
     * 根据售货机编号查询货道列表
     * @param innerCode
     * @return List<ChannelVo>
     */
    @Override
    public List<ChannelVo> selectChannelVoByInnerCode(String innerCode) {
        return channelMapper.selectChannelVoByInnerCode(innerCode);
    }
}
