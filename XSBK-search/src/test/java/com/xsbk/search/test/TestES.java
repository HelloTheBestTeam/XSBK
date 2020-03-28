package com.xsbk.search.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import com.xsbk.search.common.msg.Msg;

public class TestES {
	
	RestHighLevelClient restHighLevelClient;
	
	public TestES() {
		this.restHighLevelClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
	}
	
	public static void main(String[] args) throws Exception {
		TestES es = new TestES();
		
		User u = new User();
		u.setAge(18);
		u.setName("小米");
		
		//删除索引
		//es.deleteIndex("test");
		
		//创建索引
		/*Map<String,String> propertiesMap = new HashMap<String,String>();
		propertiesMap.put("username", "text");
		propertiesMap.put("age", "integer");
		es.createIndex("test01","user",propertiesMap);*/
		
		//插入文档
		Map<String, Object> dataMap1 = new HashMap<>();
		dataMap1.put("username", u.getName());
		dataMap1.put("age", u.getAge());
		IndexRequest indexRequest = new IndexRequest("test01", "user", "1");
		es.addOrUpdateDoc(indexRequest , dataMap1);
		
		String[] includes = {"username","age"};
		String[] excludes = {};
		es.getDocument("1", "user", "test01", includes, excludes);
	}

	// 创建索引请求参数配置
	protected CreateIndexRequest getCreateIndexRequest(String index) throws Exception {
		CreateIndexRequest cir = new CreateIndexRequest(index);
		return cir;
	}

	// 设置mapping
	protected void mappingSetting(CreateIndexRequest cir, String type, Map<String,String> propertiesMap) {
		//解析properties成字符串
		String pr = "";
		if(propertiesMap == null) {
			throw new RuntimeException(Msg.DATA_NULL);
		}
		Set<Entry<String, String>> entrySet = propertiesMap.entrySet();
		int index = 0;
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();
			if(index == entrySet.size() - 1) {
				pr += "\"" + key + "\"" + " : { " + "\"type\" : \"" + value + "\" }\n";
			} else {
				pr += "\"" + key + "\"" + " : { " + "\"type\" : \"" + value + "\" },\n";
			}
			index++;
		}
		
		cir.source("{\n" +
                    "    \"settings\" : {\n" +
                    "        \"number_of_shards\" : 1,\n" +
                    "        \"number_of_replicas\" : 0\n" +
                    "    },\n" +
                    "    \"mappings\" : {\n" +
                    "        \"" +type+ "\" : {\n" +
                    "            \"properties\" : {\n" +
                    				pr +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +      
                    "}", XContentType.JSON);
	}

	// 发送创建索引请求
	public boolean createIndex(String index, String type, Map<String,String> propertiesMap) throws Exception {
		CreateIndexRequest createIndexRequest = getCreateIndexRequest(index);
		mappingSetting(createIndexRequest, type, propertiesMap);
		CreateIndexResponse createRes = null;
		try {
			createRes = restHighLevelClient.indices().create(createIndexRequest);
		} catch (Exception e) {
			e.printStackTrace();
			//索引存在，创建失败
			return false;
		}
		boolean acknowledged = createRes.isAcknowledged();
		if (!acknowledged) {
			return false;
		}
		return true;
	}

	// 添加文档
	public boolean addOrUpdateDoc(IndexRequest indexRequest, Map<String, Object> dataMap) {
		if (indexRequest == null || dataMap == null) {
			throw new RuntimeException(Msg.DATA_NULL);
		}
		indexRequest.source(dataMap);
		try {
			IndexResponse res = restHighLevelClient.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 根据文档id获取文档
	public Map<String, Object> getDocument(String id, String type, String index, String[] includes, String[] excludes) {
		GetRequest getRequest = new GetRequest(index, type, id);
		FetchSourceContext fsc = new FetchSourceContext(true, includes, excludes);
		getRequest.fetchSourceContext(fsc);
		try {
			GetResponse getResponse = restHighLevelClient.get(getRequest);
			Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
			return sourceAsMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 关键字搜索
	public void search(SearchRequest searchRequest, String keyword) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		ssb.query(QueryBuilders.termQuery("title", keyword));
		SearchRequest sr = searchRequest.source(ssb);
		try {
			SearchResponse res = restHighLevelClient.search(sr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//删除索引
	protected void deleteIndex(String index) throws IOException {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
		restHighLevelClient.indices().delete(deleteIndexRequest);
	}
}
