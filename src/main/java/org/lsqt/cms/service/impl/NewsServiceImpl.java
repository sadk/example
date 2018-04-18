package org.lsqt.cms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lsqt.cms.model.Content;
import org.lsqt.cms.model.ContentQuery;
import org.lsqt.cms.model.News;
import org.lsqt.cms.model.NewsQuery;
import org.lsqt.cms.model.Tags;
import org.lsqt.cms.model.TagsQuery;
import org.lsqt.cms.service.NewsService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;

@Service
public class NewsServiceImpl implements NewsService {
	@Inject private Db db;
	
	public Page<News>  queryForPage(NewsQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), News.class, query);
	}

	public List<News> getAll(){
		  return db.queryForList("getAll", News.class);
	}
	
	/**
	 * 保存新闻基本信息
	 */
	public News saveOrUpdate(News model) {
		return db.saveOrUpdate(model);
	}

	/**
	 * 保存新闻基本信息和新闻详细内容
	 */
	public News saveOrUpdate(News model,String content,String channelIds) {
		if(StringUtil.isNotBlank(model.getTags())) {
			Set<String> set = new HashSet<>(StringUtil.split(model.getTags(), ","));
			model.setTags(StringUtil.join(set, ","));
		}
		db.saveOrUpdate(model);
		
		if(model.getId()==null){
			Content cnt = new Content();
			cnt.setAppCode(model.getAppCode());
			cnt.setCode("news_"+model.getCode());
			cnt.setContent(content);
			cnt.setTitle(model.getTitle());
			cnt.setType(Content.TYPE_NEWS);
			cnt.setObjectId(model.getId());
			db.save(cnt);
		}else {
			ContentQuery query = new ContentQuery();
			query.setObjectId(model.getId());
			Content m = db.queryForObject("queryForPage", Content.class, query);
			if(m!=null) {
				m.setObjectId(model.getId());
				m.setContent(content);
				m.setTitle(model.getTitle());
				db.update(m, "content","title","objectId");
			} else {
				m = new Content();
				m.setObjectId(model.getId());
				m.setContent(content);
				m.setType(Content.TYPE_NEWS);
				m.setEnable(News.ENABLE_启用);
				m.setAppCode(model.getAppCode());
				m.setTitle(model.getTitle());
				db.save(m);
			}
		}
		
		saveTagsIfExists(model.getTags());
		
		saveOrUpdateChannels(model,channelIds);
		return model;
	}

	/**
	 * 保存新闻所属的栏目
	 * 
	 * @param model
	 * @param channelIds
	 */
	private void saveOrUpdateChannels(News model, String channelIds) {
		if (StringUtil.isNotBlank(channelIds)) {
			String sql = String.format("delete from cms_mid_channel_news where news_id=?");
			db.executeUpdate(sql, model.getId());
			
			for(Long cId: StringUtil.split(Long.class, channelIds, ",")) {
				String sql2 = "insert into cms_mid_channel_news(channel_id,news_id) values(?,?)";
				db.executeUpdate(sql2, cId,model.getId());
			}
			
		}
	}
	
	/**
	 * 保存tag到标签表
	 * @param tagsText
	 */
	private void saveTagsIfExists(String tagsText) {
		if(StringUtil.isNotBlank(tagsText)) {
			Set<String> tags = new HashSet<>(StringUtil.split(tagsText, ","));
			if(ArrayUtil.isNotBlank(tags)) {
				TagsQuery tq = new TagsQuery();
				tq.setNameList(new ArrayList<>(tags));
				List<Tags> tagList = db.queryForList("queryForPage", Tags.class, tq);
				if(ArrayUtil.isNotBlank(tagList)) {
					for(Tags e: tagList) {
						tags.remove(e.getName());
					}
				}
			}
			
			if(ArrayUtil.isNotBlank(tags)) {
				List<Tags> modelList = new ArrayList<>();
				int i=0;
				for(String e: tags) {
					Tags t = new Tags();
					t.setName(e);
					t.setCode(System.currentTimeMillis()+""+i++);
					modelList.add(t);
				}
				db.batchSave(modelList);
			}
		}
	}

	
	public int deleteById(Long ... ids) {
		return db.deleteById(News.class, Arrays.asList(ids).toArray());
	}

	@Override
	public List<News> queryForList(NewsQuery query) {
		 return db.queryForList("queryForPage", News.class, query);
	}

}
