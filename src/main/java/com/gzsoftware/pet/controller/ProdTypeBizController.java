package com.gzsoftware.pet.controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzsoftware.pet.entity.po.Admin;
import com.gzsoftware.pet.entity.po.ProdTypeBiz;
import com.gzsoftware.pet.entity.vo.DataTablesRequest;
import com.gzsoftware.pet.entity.vo.Result;
import com.gzsoftware.pet.service.ProdTypeBizService;
@Controller
@RequestMapping("/prodTypeBiz")
public class ProdTypeBizController extends BaseController {
	private static Log log = LogFactory.getLog(ProdTypeBizController.class);
	
	@Resource
	private ProdTypeBizService prodTypeBizService;
	/**
	 * 获取所有prodPhy
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeBizList", method = RequestMethod.POST)
	public  Result getProdTypeBizList(@RequestBody DataTablesRequest dtRequest)  {
		List<ProdTypeBiz> phyList = prodTypeBizService.getProdTypeBizList(dtRequest);
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList,prodTypeBizService.countAll(dtRequest),prodTypeBizService.countAll(dtRequest));
	}
	
	/**
	 * 获取所有prodBiz
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeBizSelect", method = RequestMethod.GET)
	public  Result getProdTypeBizSelect()  {
		List<ProdTypeBiz> phyList = prodTypeBizService.getProdTypeBizSelect();
		return new Result(Result.RESULT_FLAG_SUCCESS,phyList);
	}
	
	/**
	 * 根据ID获取prodTypeBiz
	 * **/

	@ResponseBody
	@RequestMapping(value = "/getProdTypeBiz", method = RequestMethod.GET)
	public Result getProdTypeBiz(@RequestParam("id") int id) {
		ProdTypeBiz prodTypeBiz = prodTypeBizService.getProdTypeBiz(id);
		return new Result(Result.RESULT_FLAG_SUCCESS,prodTypeBiz);
	}

	
	/**
	 * 新增 ProdTypeBiz
	 * **/
	@ResponseBody
	@RequestMapping(value = "/addProdTypeBiz", method = RequestMethod.POST)
	public Result addProdTypeBiz(@RequestBody ProdTypeBiz prodTypeBiz,HttpSession session) {
		prodTypeBiz.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		//默认是启用
		if(prodTypeBiz.getIsEnable()==null){
			prodTypeBiz.setIsEnable("1");
		}
		prodTypeBiz.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt = prodTypeBizService.addProdTypeBiz(prodTypeBiz);
		if(effCnt > 0){
			log.debug("添加物理类目成功");
			return new Result(Result.RESULT_FLAG_SUCCESS,"添加成功",prodTypeBiz);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"添加失败",prodTypeBiz);
		}
		
	
	}
	
	
	/**
	 * 更新 ProdTypeBiz
	 * **/
	@ResponseBody
	@RequestMapping(value = "/updateProdTypeBiz", method = RequestMethod.POST)
	public Result updateProdTypeBiz(@RequestBody ProdTypeBiz prodTypeBiz,HttpSession session) {
		prodTypeBiz.setLastUpdateTime(new Date());
		Admin currentAdmin = super.getCurrentAdmin(session);
		if(currentAdmin==null){
			return new Result(Result.RESULT_FLAG_FAIL,"会话超时，请重新登录");
		}
		prodTypeBiz.setLastUpdateAdminId(currentAdmin.getId());
		int effCnt =prodTypeBizService.updateProdTypeBiz(prodTypeBiz);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"更新成功",prodTypeBiz);
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"更新失败",prodTypeBiz);
		}
	}
	
	
	/**
	 * 删除 ProdTypeBiz
	 * **/
	@ResponseBody
	@RequestMapping(value = "/deleteProdTypeBiz", method = RequestMethod.GET)
	public Result deleteProdTypeBiz(@RequestParam("id") int id) {
		int effCnt = prodTypeBizService.deleteProdTypeBiz(id);
		if(effCnt > 0){
			return new Result(Result.RESULT_FLAG_SUCCESS,"success");
		}else{
			return new Result(Result.RESULT_FLAG_FAIL,"fail");
		}
	}
}
