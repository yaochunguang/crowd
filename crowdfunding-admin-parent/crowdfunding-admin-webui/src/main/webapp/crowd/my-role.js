// ����ר�ŵĺ��������ڷ���Auth��ģ̬������ʾAuth�����νṹ����
function fillAuthTree() {
	
	// 1.����Ajax�����ѯAuth����
	var ajaxReturn = $.ajax({
		"url":"assgin/get/all/auth.json",
		"type":"post",
		"dataType":"json",
		"async":false
	});
	
	if(ajaxReturn.status != 200) {
		layer.msg("�����������Ӧ״̬���ǣ�"+ajaxReturn.status+" ˵���ǣ�"+ajaxReturn.statusText);
		return ;
	}
	
	// 2.����Ӧ����л�ȡAuth��JSON����
	// �ӷ������˲�ѯ����list����Ҫ��װ�����νṹ���������ǽ���zTreeȥ��װ
	var authList = ajaxReturn.responseJSON.data;
	
	// 3.׼����zTree�������õ�JSON����
	var setting = {
		"data": {
			"simpleData": {
				
				// ������JSON����
				"enable": true,
				
				// ʹ��categoryId���Թ������ڵ㣬����Ĭ�ϵ�pId��
				"pIdKey": "categoryId"
			},
			"key": {
				// ʹ��title������ʾ�ڵ����ƣ�����Ĭ�ϵ�name��Ϊ��������
				"name": "title"
			}
		},
		"check": {
			"enable": true
		}
	};
	
	// 4.�������νṹ
	// <ul id="authTreeDemo" class="ztree"></ul>
	$.fn.zTree.init($("#authTreeDemo"), setting, authList);

	// ��ȡzTreeObj����
	var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
	
	// ����zTreeObj����ķ������ѽڵ�չ��
	zTreeObj.expandAll(true);
	
	// 5.��ѯ�ѷ����Auth��id��ɵ�����
	ajaxReturn = $.ajax({
		"url":"assign/get/assigned/auth/id/by/role/id.json",
		"type":"post",
		"data":{
			"roleId":window.roleId
		},
		"dataType":"json",
		"async":false
	});
	
	if(ajaxReturn.status != 200) {
		layer.msg("�����������Ӧ״̬���ǣ�"+ajaxReturn.status+" ˵���ǣ�"+ajaxReturn.statusText);
		return ;
	}
	
	// ����Ӧ����л�ȡauthIdArray
	var authIdArray = ajaxReturn.responseJSON.data;
	
	// 6.����authIdArray�����νṹ�ж�Ӧ�Ľڵ㹴ѡ��
	// �ٱ���authIdArray
	for(var i = 0; i < authIdArray.length; i++) {
		var authId = authIdArray[i];
		
		// �ڸ���id��ѯ���νṹ�ж�Ӧ�Ľڵ�
		var treeNode = zTreeObj.getNodeByParam("id", authId);
		
		// �۽�treeNode����Ϊ����ѡ
		
		// checked����Ϊtrue��ʾ�ڵ㹴ѡ
		var checked = true;
		
		// checkTypeFlag����Ϊfalse����ʾ��������������������Ϊ�˱���Ѳ��ù�ѡ�Ĺ�ѡ��
		var checkTypeFlag = false;
		
		// ִ��
		zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
	}
}

// ����ר�ŵĺ�����ʾȷ��ģ̬��
function showConfirmModal(roleArray) {
	
	// ��ģ̬��
	$("#confirmModal").modal("show");
	
	// ����ɵ�����
	$("#roleNameDiv").empty();
	
	// ��ȫ�ֱ�����Χ��������������Ž�ɫid
	window.roleIdArray = [];
	
	// ����roleArray����
	for(var i = 0; i < roleArray.length; i++) {
		var role = roleArray[i];
		var roleName = role.roleName;
		$("#roleNameDiv").append(roleName+"<br/>");
		
		var roleId = role.roleId;
		
		// ������������push()����������Ԫ��
		window.roleIdArray.push(roleId);
	}
	
}

// ִ�з�ҳ������ҳ��Ч�����κ�ʱ�������������������¼���ҳ��
function generatePage() {
	
	// 1.��ȡ��ҳ����
	var pageInfo = getPageInfoRemote();
	
	// 2.�����
	fillTableBody(pageInfo);
	
}

// Զ�̷��ʷ������˳����ȡpageInfo����
function getPageInfoRemote() {
	
	// ����$.ajax()�����������󲢽���$.ajax()�����ķ���ֵ
	var ajaxResult = $.ajax({
		"url": "role/get/page/info.json",
		"type":"post",
		"data": {
			"pageNum": window.pageNum,
			"pageSize": window.pageSize,
			"keyword": window.keyword
		},
		"async":false,
		"dataType":"json"
	});
	
	console.log(ajaxResult);
	
	// �жϵ�ǰ��Ӧ״̬���Ƿ�Ϊ200
	var statusCode = ajaxResult.status;
	
	// �����ǰ��Ӧ״̬�벻��200��˵�������˴�������������������ʾ��ʾ��Ϣ���õ�ǰ����ִֹͣ��
	if(statusCode != 200) {
		layer.msg("ʧ�ܣ���Ӧ״̬��="+statusCode+" ˵����Ϣ="+ajaxResult.statusText);
		return null;
	}
	
	// �����Ӧ״̬����200��˵��������ɹ�����ȡpageInfo
	var resultEntity = ajaxResult.responseJSON;
	
	// ��resultEntity�л�ȡresult����
	var result = resultEntity.result;
	
	// �ж�result�Ƿ�ɹ�
	if(result == "FAILED") {
		layer.msg(resultEntity.message);
		return null;
	}
	
	// ȷ��resultΪ�ɹ����ȡpageInfo
	var pageInfo = resultEntity.data;
	
	// ����pageInfo
	return pageInfo;
}

// �����
function fillTableBody(pageInfo) {
	
	// ���tbody�еľɵ�����
	$("#rolePageBody").empty();
	
	// ���������Ϊ����û���������ʱ����ʾҳ�뵼����
	$("#Pagination").empty();
	
	// �ж�pageInfo�����Ƿ���Ч
	if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
		$("#rolePageBody").append("<tr><td colspan='4' align='center'>��Ǹ��û�в�ѯ�������������ݣ�</td></tr>");
		
		return ;
	}
	
	// ʹ��pageInfo��list�������tbody
	for(var i = 0; i < pageInfo.list.length; i++) {
		
		var role = pageInfo.list[i];
		
		var roleId = role.id;
		
		var roleName = role.name;
		
		var numberTd = "<td>"+(i+1)+"</td>";
		var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
		var roleNameTd = "<td>"+roleName+"</td>";
		
		var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
		
		// ͨ��button��ǩ��id���ԣ����������ʵҲ���ԣ���roleIdֵ���ݵ�button��ť�ĵ�����Ӧ�����У��ڵ�����Ӧ������ʹ��this.id
		var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		
		// ͨ��button��ǩ��id���ԣ����������ʵҲ���ԣ���roleIdֵ���ݵ�button��ť�ĵ�����Ӧ�����У��ڵ�����Ӧ������ʹ��this.id
		var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
		
		var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
		
		var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
		
		$("#rolePageBody").append(tr);
	}
	
	// ���ɷ�ҳ������
	generateNavigator(pageInfo);
}

// ���ɷ�ҳҳ�뵼����
function generateNavigator(pageInfo) {
	
	// ��ȡ�ܼ�¼��
	var totalRecord = pageInfo.total;
	
	// �����������
	var properties = {
		"num_edge_entries": 3,
		"num_display_entries": 5,
		"callback": paginationCallBack,
		"items_per_page": pageInfo.pageSize,
		"current_page": pageInfo.pageNum - 1,
		"prev_text": "��һҳ",
		"next_text": "��һҳ"
	}
	
	// ����pagination()����
	$("#Pagination").pagination(totalRecord, properties);
}

// ��ҳʱ�Ļص�����
function paginationCallBack(pageIndex, jQuery) {
	
	// �޸�window�����pageNum����
	window.pageNum = pageIndex + 1;
	
	// ���÷�ҳ����
	generatePage();
	
	// ȡ��ҳ�볬���ӵ�Ĭ����Ϊ
	return false;
	
}