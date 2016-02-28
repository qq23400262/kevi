<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = com.fh.util.PageUtil.getPreBasePath(request)+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="inventory/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="INVENTORY_ID" id="INVENTORY_ID" value="${pd.INVENTORY_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">款式年份:</td>
								<td><input type="number" name="YEARS" id="YEARS" value="${pd.YEARS}" maxlength="32" placeholder="这里输入款式年份" title="款式年份" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">款式编号:</td>
								<td><input type="text" name="CODE" id="CODE" value="${pd.CODE}" maxlength="255" placeholder="这里输入款式编号" title="款式编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">款式季节:</td>
								<td><input type="text" name="SEASON" id="SEASON" value="${pd.SEASON}" maxlength="255" placeholder="这里输入款式季节" title="款式季节" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">款式名称:</td>
								<td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里输入款式名称" title="款式名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">款式现价:</td>
								<td><input type="number" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入款式现价" title="款式现价" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">颜色:</td>
								<td><input type="text" name="COLOR" id="COLOR" value="${pd.COLOR}" maxlength="255" placeholder="这里输入颜色" title="颜色" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">S:</td>
								<td><input type="number" name="SS" id="SS" value="${pd.SS}" maxlength="32" placeholder="这里输入S" title="S" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">M:</td>
								<td><input type="number" name="SM" id="SM" value="${pd.SM}" maxlength="32" placeholder="这里输入M" title="M" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">L:</td>
								<td><input type="number" name="SL" id="SL" value="${pd.SL}" maxlength="32" placeholder="这里输入L" title="L" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">XL:</td>
								<td><input type="number" name="SXL" id="SXL" value="${pd.SXL}" maxlength="32" placeholder="这里输入XL" title="XL" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">XXL:</td>
								<td><input type="number" name="SXXL" id="SXXL" value="${pd.SXXL}" maxlength="32" placeholder="这里输入XXL" title="XXL" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">总库存:</td>
								<td><input type="number" name="TOTAL" id="TOTAL" value="${pd.TOTAL}" maxlength="32" placeholder="这里输入总库存" title="总库存" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">日期:</td>
								<td><input class="span10 date-picker" name="DATE" id="DATE" value="${pd.DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="日期" title="日期" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#YEARS").val()==""){
				$("#YEARS").tips({
					side:3,
		            msg:'请输入款式年份',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#YEARS").focus();
			return false;
			}
			if($("#CODE").val()==""){
				$("#CODE").tips({
					side:3,
		            msg:'请输入款式编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CODE").focus();
			return false;
			}
			if($("#SEASON").val()==""){
				$("#SEASON").tips({
					side:3,
		            msg:'请输入款式季节',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SEASON").focus();
			return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入款式名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#PRICE").val()==""){
				$("#PRICE").tips({
					side:3,
		            msg:'请输入款式现价',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PRICE").focus();
			return false;
			}
			if($("#COLOR").val()==""){
				$("#COLOR").tips({
					side:3,
		            msg:'请输入颜色',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#COLOR").focus();
			return false;
			}
			if($("#SS").val()==""){
				$("#SS").tips({
					side:3,
		            msg:'请输入S',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SS").focus();
			return false;
			}
			if($("#SM").val()==""){
				$("#SM").tips({
					side:3,
		            msg:'请输入M',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SM").focus();
			return false;
			}
			if($("#SL").val()==""){
				$("#SL").tips({
					side:3,
		            msg:'请输入L',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SL").focus();
			return false;
			}
			if($("#SXL").val()==""){
				$("#SXL").tips({
					side:3,
		            msg:'请输入XL',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SXL").focus();
			return false;
			}
			if($("#SXXL").val()==""){
				$("#SXXL").tips({
					side:3,
		            msg:'请输入XXL',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SXXL").focus();
			return false;
			}
			if($("#TOTAL").val()==""){
				$("#TOTAL").tips({
					side:3,
		            msg:'请输入总库存',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TOTAL").focus();
			return false;
			}
			if($("#DATE").val()==""){
				$("#DATE").tips({
					side:3,
		            msg:'请输入日期',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#DATE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>