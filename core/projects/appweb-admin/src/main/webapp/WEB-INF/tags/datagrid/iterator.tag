<%@ tag pageEncoding="UTF-8" import="org.onetwo.common.web.view.jsp.grid.*"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib prefix="gridRender" tagdir="/WEB-INF/tags/datagrid" %>

<%@ attribute name="row" type="org.onetwo.common.web.view.jsp.datagrid.DataRowTagBean" required="true"%>

<c:if test="${row.renderHeader}">
	<thead>
		<tr class="error" ${row.gridAttributesHtml}>
		<c:forEach items="${row.fields}" var="field">
			<th ${field.gridAttributesHtml} class="${field.cssClass}" style="${field.cssStyle}">
			
			<%
				FieldTagBean fieldBean = (FieldTagBean) getJspContext().getAttribute("field");
				String linkText = fieldBean.getGridLabelHtml();
				/* if(fieldBean.isOrderable()){
					linkText = "<a href='";
					linkText += fieldBean.appendOrderBy(fieldBean.getRowTagBean().getGridTagBean().getAction()) +"' ";
							
					if(fieldBean.getGrid().isFormPagination()){
						linkText += " data-method='post'";
					}
					linkText += ">";
					linkText +=fieldBean.getLabel();
					
					if (fieldBean.isOrdering() && fieldBean.getOrderType()==":desc"){
						linkText += "↑";
					}else if(fieldBean.isOrdering() && fieldBean.getOrderType()==":asc"){
						linkText += "↓";
					}
					
					linkText += "</a>";
					out.println(linkText);
				}else{
					out.println(fieldBean.getLabel());
				} */
				out.println(linkText);
				
				if(fieldBean.isCheckbox()){
					%>
					<input type="checkbox" name="all_<%=fieldBean.getName() %>" value="" id="id_all_<%=fieldBean.getName() %>" class="dg-checkbox-all"/>
					<%
				}
			%>
			
			</th>
		</c:forEach>
		</tr>
	</thead>
</c:if>
	
<c:if test="${row.gridTagBean.page.result.size() <= 0}">
	<tr class="page-no-datas"><td colspan="${row.gridTagBean.colspan}" style="text-align:center">没有数据</td></tr>
</c:if>

<c:forEach items="${row.datas}" var="entity" varStatus="itStatus">
<tr class="${itStatus.index%2==0?'warning':'success' }">
	<c:forEach items="${row.fields}" var="field">
		<gridRender:field entity="${entity}" field="${field }"></gridRender:field>
	</c:forEach>
</tr>
</c:forEach>

