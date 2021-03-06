<%@tag import="org.onetwo.common.web.view.jsp.TagUtils"%>
<%@ tag pageEncoding="UTF-8" import="org.onetwo.common.web.view.jsp.grid.*"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib prefix="gridRender" tagdir="/WEB-INF/tags/tags_new/datagrid" %>

<%@ attribute name="page" type="org.onetwo.common.utils.Page" required="true"%>
<%@ attribute name="action" type="java.lang.String" required="true"%>
<%@ attribute name="formPagination" type="java.lang.Boolean" required="true"%>
<%@ attribute name="remote" type="java.lang.Boolean" required="true" %>
<%@ attribute name="ajaxName" type="java.lang.String"%>

<%
	String linkText = "";
	if(remote!=null && remote){
		linkText = " remote='true' ajaxName='"+ajaxName+"'";
	}
	if(formPagination){
		linkText += " data-method='post'";
	}else if(remote!=null && remote){
		linkText += " data-method='get'";
	}
%>
<div class="pagination clearfix">
	
	<div class="controls">
	 每页显示 <input id="btnPageSize" name="pageSize" style="width: 30px;
height: 22px;
text-align: center;
font-size: 14px;
margin: 0;
padding: 0;
border-radius: 4px;
border: 1px solid #ccc;
text-align: center;
font-weight: 700;" value="${page.pageSize}" action="${TagUtils.pageLink(action, 1)}" remote="${remote}" ajaxName="${ajaxName }"/> 条记录 
	&nbsp;
	<%
	if(page.isHasPre()){ %>
		<a href="<%= TagUtils.pageLink(action, 1)%>" <%=linkText %>><i class="fa fa-fast-backward"></i></a>
        <a href="<%= TagUtils.pageLink(action, page.getPrePage())%>" <%=linkText %>><i class="fa fa-chevron-left"></i></a>
	<%} 
	if(page.getEndPage()!=0){
		String pageClass = "";
		for(int pageNumber=page.getStartPage(); pageNumber<=page.getEndPage(); pageNumber++){
			if(page.getPageNo()==pageNumber){
				pageClass="class='active'";
			}
			else pageClass="";
		%>
			<a href="<%=TagUtils.pageLink(action, pageNumber) %>" <%=pageClass%> <%=linkText %> ><%=pageNumber %></a>
		<%
		}//end for
	}
	
	if(page.isHasNext()){%>
		<a href="<%=TagUtils.pageLink(action, page.getNextPage()) %>" <%=linkText %>><i class="fa fa-chevron-right"></i></a>
        <a href="<%=TagUtils.pageLink(action, page.getTotalPages()) %>" <%=linkText %>><i class="fa fa-fast-forward"></i></a>
	<%} //end if%>
</div>
	<span class="stat"><span>共</span>${page.totalCount}<span>条记录</span> <strong>${page.pageNo}/${page.totalPages}页</strong></span>
</div>