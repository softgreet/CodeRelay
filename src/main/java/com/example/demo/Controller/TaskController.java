package com.example.demo.Controller;

import com.example.demo.data.TaskMapper;
import com.example.demo.util.DataSourceContextHolder;
import io.netty.util.concurrent.CompleteFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class TaskController {

    private final TaskMapper taskMapper;
    private final CacheService cacheService;

    @Autowired
    public TaskController(TaskMapper taskMapper, CacheService cacheService) {
        this.taskMapper = taskMapper;
        this.cacheService = cacheService;
    }

    @RequestMapping("/api/{db}")
    public List<String> getAllTables(@PathVariable String db){
        DataSourceContextHolder.set(db);
        List<String> res = taskMapper.getAllTables();
        DataSourceContextHolder.clear();
        return res;

    }

    @RequestMapping("/api/{db}/{table}")
    public List<Map<String, Object>> getTableData(@PathVariable String db, @PathVariable String table){
        DataSourceContextHolder.set(db);
        List<Map<String, Object>> res = taskMapper.fetData(table);
        DataSourceContextHolder.clear();
        return res;

    }

    @RequestMapping("/{db}/{table}")
    public List<Map<String, Object>> getStructure(@PathVariable String db, @PathVariable String table){
        DataSourceContextHolder.set(db);
        List<Map<String, Object>> res = taskMapper.getStructure(table);
        for(Map<String,Object> m:res){
            if(m.get("Key").equals("PRI")){
                System.out.println(m.get("Type"));
                System.out.println(m.get("Field"));
            }
        }
        DataSourceContextHolder.clear();
        return res;
    }

    @Async
    public CompletableFuture<Boolean> preLabel(int datasetId, int taskId, int batchNum){
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }



}
