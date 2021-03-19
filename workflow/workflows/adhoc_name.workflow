{
	"contents": {
		"f4975d2f-48ed-4b26-af10-69da7730c80f": {
			"classDefinition": "com.sap.bpm.wfs.Model",
			"id": "adhoc_name",
			"subject": "adhoc_name",
			"customAttributes": [],
			"name": "adhoc_name",
			"documentation": "test adhoc",
			"lastIds": "62d7f4ed-4063-4c44-af8b-39050bd44926",
			"events": {
				"11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3": {
					"name": "StartEvent1"
				},
				"2798f4e7-bc42-4fad-a248-159095a2f40a": {
					"name": "EndEvent1"
				},
				"afbd3f14-2dfd-44f3-8194-931fff9ad523": {
					"name": "EndEvent2"
				},
				"4e440c6f-aeeb-45bd-b412-e025264ea073": {
					"name": "EndEvent3"
				},
				"fe3536db-e35c-48b5-a95a-adb681305bb6": {
					"name": "EndEvent5"
				},
				"ef79d601-5202-4e1a-8f85-d6587bcf76f8": {
					"name": "EndEvent6"
				}
			},
			"activities": {
				"78ed913a-e099-489b-ad32-087b7b175485": {
					"name": "Check Adhoc Type"
				},
				"c4fd6e88-ee6a-46fe-a35c-f72872ee6cf0": {
					"name": "STEP1 MANAGER"
				},
				"73bbe769-0a83-436f-9215-30652c1f12bf": {
					"name": "ServiceTask1"
				},
				"e4b655e9-f50a-4256-9e31-bf67147ffebd": {
					"name": "IsApproved"
				},
				"d4192e6b-7df5-431e-ae8a-7b1546e5a157": {
					"name": "Check AdhocType"
				},
				"2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe": {
					"name": "ServiceTask2"
				},
				"0cdd5704-7705-44d5-a768-7177a671987e": {
					"name": "STEP PLANNER2"
				},
				"44394e5c-f258-4fbc-93f2-8aa58fced2ff": {
					"name": "ServiceTask3"
				},
				"f93e58ba-2448-471c-8b69-150159cfa460": {
					"name": "IS APPROVED"
				},
				"83a581ee-5c56-468a-8569-e5a363bfd845": {
					"name": "ScriptTask3"
				},
				"c5ddc870-b40f-4900-bd00-098dc4a32147": {
					"name": "ScriptTask4"
				},
				"431a99cf-4140-4e15-9de7-cd59092faa23": {
					"name": "ScriptTask5"
				},
				"d0a19dc4-2bff-4aec-8df2-ae448a813a4c": {
					"name": "ScriptTask6"
				},
				"8245702a-ce8d-461e-865a-dbb2c2f6759e": {
					"name": "ServiceTask4"
				}
			},
			"sequenceFlows": {
				"c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f": {
					"name": "SequenceFlow1"
				},
				"70f95383-dca6-49f3-a014-8db5e210e426": {
					"name": "TYPE4"
				},
				"498c696d-21d0-44ab-abca-1b08548a4d3b": {
					"name": "SequenceFlow3"
				},
				"4959ca1d-5030-4dc5-b509-1830778d8369": {
					"name": "Not Type4"
				},
				"3dff84db-b2a0-4a23-8324-72cc5cadb1c6": {
					"name": "Approved"
				},
				"ba86078b-35d8-4898-88d5-143541962920": {
					"name": "Rejected"
				},
				"90abf7bd-93f2-4803-8075-e047a9e67d9c": {
					"name": "Type3"
				},
				"7199593d-4ed2-48b4-9422-20ea7dd1c8bd": {
					"name": "NOT TYPE3"
				},
				"d90be860-76c5-44f8-9c55-d6c4a42e65e9": {
					"name": "SequenceFlow11"
				},
				"98f9da79-aab4-41e3-be03-7a954567e845": {
					"name": "REJECT"
				},
				"4dc8e57a-eabf-4972-a969-31b46b83e32e": {
					"name": "Approved"
				},
				"3c7f7ba0-53db-4ac3-85e2-c4d87277d50e": {
					"name": "SequenceFlow14"
				},
				"1fc7930a-f1e3-41af-82c5-47df299f644e": {
					"name": "SequenceFlow15"
				},
				"ebcab00a-e8a2-4d2d-90f1-477435dd7b65": {
					"name": "SequenceFlow16"
				},
				"f5b5e72b-b861-4bcf-98c6-6dfa43f427b0": {
					"name": "SequenceFlow18"
				},
				"50d8a7c9-6e9f-4985-beb4-792fb1aa764d": {
					"name": "SequenceFlow19"
				},
				"9f230ccd-62a4-4871-89b5-1a55c57a1a62": {
					"name": "SequenceFlow20"
				},
				"941ef3e9-90a6-40d0-af82-f5e53d56005d": {
					"name": "SequenceFlow21"
				},
				"6a481d95-905a-4996-aedc-600ca25f0831": {
					"name": "SequenceFlow22"
				}
			},
			"diagrams": {
				"42fa7a2d-c526-4a02-b3ba-49b5168ba644": {}
			}
		},
		"11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3": {
			"classDefinition": "com.sap.bpm.wfs.StartEvent",
			"id": "startevent1",
			"name": "StartEvent1"
		},
		"2798f4e7-bc42-4fad-a248-159095a2f40a": {
			"classDefinition": "com.sap.bpm.wfs.EndEvent",
			"id": "endevent1",
			"name": "EndEvent1"
		},
		"afbd3f14-2dfd-44f3-8194-931fff9ad523": {
			"classDefinition": "com.sap.bpm.wfs.EndEvent",
			"id": "endevent2",
			"name": "EndEvent2"
		},
		"4e440c6f-aeeb-45bd-b412-e025264ea073": {
			"classDefinition": "com.sap.bpm.wfs.EndEvent",
			"id": "endevent3",
			"name": "EndEvent3"
		},
		"fe3536db-e35c-48b5-a95a-adb681305bb6": {
			"classDefinition": "com.sap.bpm.wfs.EndEvent",
			"id": "endevent5",
			"name": "EndEvent5"
		},
		"ef79d601-5202-4e1a-8f85-d6587bcf76f8": {
			"classDefinition": "com.sap.bpm.wfs.EndEvent",
			"id": "endevent6",
			"name": "EndEvent6"
		},
		"78ed913a-e099-489b-ad32-087b7b175485": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway1",
			"name": "Check Adhoc Type",
			"default": "4959ca1d-5030-4dc5-b509-1830778d8369"
		},
		"c4fd6e88-ee6a-46fe-a35c-f72872ee6cf0": {
			"classDefinition": "com.sap.bpm.wfs.UserTask",
			"subject": "Adhoc Manager Approval",
			"description": "Manager approval task",
			"priority": "MEDIUM",
			"isHiddenInLogForParticipant": false,
			"supportsForward": false,
			"userInterface": "sapui5://comincturelchadhocApproval/com.incture.lch.adhocApproval",
			"recipientUsers": "${context.manager}",
			"recipientGroups": "",
			"userInterfaceParams": [],
			"customAttributes": [],
			"id": "usertask1",
			"name": "STEP1 MANAGER"
		},
		"73bbe769-0a83-436f-9215-30652c1f12bf": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_token",
			"path": "oauth/token?grant_type=client_credentials",
			"httpMethod": "GET",
			"responseVariable": "${context.responseMessage}",
			"id": "servicetask1",
			"name": "ServiceTask1"
		},
		"e4b655e9-f50a-4256-9e31-bf67147ffebd": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway2",
			"name": "IsApproved",
			"default": "ba86078b-35d8-4898-88d5-143541962920"
		},
		"d4192e6b-7df5-431e-ae8a-7b1546e5a157": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway3",
			"name": "Check AdhocType",
			"default": "7199593d-4ed2-48b4-9422-20ea7dd1c8bd"
		},
		"2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "adhocorders/updateApprovalWorkflowDetails",
			"httpMethod": "POST",
			"xsrfPath": "",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "",
			"id": "servicetask2",
			"name": "ServiceTask2"
		},
		"0cdd5704-7705-44d5-a768-7177a671987e": {
			"classDefinition": "com.sap.bpm.wfs.UserTask",
			"subject": "Adhoc Planner Approval ",
			"description": "Planner task approval",
			"priority": "MEDIUM",
			"isHiddenInLogForParticipant": false,
			"supportsForward": false,
			"userInterface": "sapui5://comincturelchadhocApproval/com.incture.lch.adhocApproval",
			"recipientUsers": "${context.planner}",
			"recipientGroups": "",
			"userInterfaceParams": [],
			"id": "usertask2",
			"name": "STEP PLANNER2"
		},
		"44394e5c-f258-4fbc-93f2-8aa58fced2ff": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/adhocorders/updateApprovalWorkflowDetails",
			"httpMethod": "POST",
			"xsrfPath": "",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "",
			"id": "servicetask3",
			"name": "ServiceTask3"
		},
		"f93e58ba-2448-471c-8b69-150159cfa460": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway4",
			"name": "IS APPROVED",
			"default": "98f9da79-aab4-41e3-be03-7a954567e845"
		},
		"83a581ee-5c56-468a-8569-e5a363bfd845": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput.js",
			"id": "scripttask3",
			"name": "ScriptTask3"
		},
		"c5ddc870-b40f-4900-bd00-098dc4a32147": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput.js",
			"id": "scripttask4",
			"name": "ScriptTask4"
		},
		"431a99cf-4140-4e15-9de7-cd59092faa23": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput.js",
			"id": "scripttask5",
			"name": "ScriptTask5"
		},
		"d0a19dc4-2bff-4aec-8df2-ae448a813a4c": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/tokenMessage.js",
			"id": "scripttask6",
			"name": "ScriptTask6"
		},
		"8245702a-ce8d-461e-865a-dbb2c2f6759e": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "adhocorders/updateWorkflow?taskId=1223&adhocOrderId=${context.adhocOrderId}&Authorization=Bearer ${context.token}",
			"httpMethod": "GET",
			"responseVariable": "${context.workflowInfo.response}",
			"headers": [],
			"id": "servicetask4",
			"name": "ServiceTask4"
		},
		"c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow1",
			"name": "SequenceFlow1",
			"sourceRef": "11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3",
			"targetRef": "78ed913a-e099-489b-ad32-087b7b175485"
		},
		"70f95383-dca6-49f3-a014-8db5e210e426": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"condition": "${context.type=='AS IS'}",
			"id": "sequenceflow2",
			"name": "TYPE4",
			"sourceRef": "78ed913a-e099-489b-ad32-087b7b175485",
			"targetRef": "83a581ee-5c56-468a-8569-e5a363bfd845"
		},
		"498c696d-21d0-44ab-abca-1b08548a4d3b": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow3",
			"name": "SequenceFlow3",
			"sourceRef": "c4fd6e88-ee6a-46fe-a35c-f72872ee6cf0",
			"targetRef": "e4b655e9-f50a-4256-9e31-bf67147ffebd"
		},
		"4959ca1d-5030-4dc5-b509-1830778d8369": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow5",
			"name": "Not Type4",
			"sourceRef": "78ed913a-e099-489b-ad32-087b7b175485",
			"targetRef": "c4fd6e88-ee6a-46fe-a35c-f72872ee6cf0"
		},
		"3dff84db-b2a0-4a23-8324-72cc5cadb1c6": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"condition": "${context.aifAction=='Approved'}",
			"id": "sequenceflow6",
			"name": "Approved",
			"sourceRef": "e4b655e9-f50a-4256-9e31-bf67147ffebd",
			"targetRef": "d4192e6b-7df5-431e-ae8a-7b1546e5a157"
		},
		"ba86078b-35d8-4898-88d5-143541962920": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow7",
			"name": "Rejected",
			"sourceRef": "e4b655e9-f50a-4256-9e31-bf67147ffebd",
			"targetRef": "afbd3f14-2dfd-44f3-8194-931fff9ad523"
		},
		"90abf7bd-93f2-4803-8075-e047a9e67d9c": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"condition": "${context.type=='Inventory'}",
			"id": "sequenceflow8",
			"name": "Type3",
			"sourceRef": "d4192e6b-7df5-431e-ae8a-7b1546e5a157",
			"targetRef": "c5ddc870-b40f-4900-bd00-098dc4a32147"
		},
		"7199593d-4ed2-48b4-9422-20ea7dd1c8bd": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow10",
			"name": "NOT TYPE3",
			"sourceRef": "d4192e6b-7df5-431e-ae8a-7b1546e5a157",
			"targetRef": "0cdd5704-7705-44d5-a768-7177a671987e"
		},
		"d90be860-76c5-44f8-9c55-d6c4a42e65e9": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow11",
			"name": "SequenceFlow11",
			"sourceRef": "0cdd5704-7705-44d5-a768-7177a671987e",
			"targetRef": "f93e58ba-2448-471c-8b69-150159cfa460"
		},
		"98f9da79-aab4-41e3-be03-7a954567e845": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow12",
			"name": "REJECT",
			"sourceRef": "f93e58ba-2448-471c-8b69-150159cfa460",
			"targetRef": "2798f4e7-bc42-4fad-a248-159095a2f40a"
		},
		"4dc8e57a-eabf-4972-a969-31b46b83e32e": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"condition": "${context.aifAction=='Approved'}",
			"id": "sequenceflow13",
			"name": "Approved",
			"sourceRef": "f93e58ba-2448-471c-8b69-150159cfa460",
			"targetRef": "431a99cf-4140-4e15-9de7-cd59092faa23"
		},
		"3c7f7ba0-53db-4ac3-85e2-c4d87277d50e": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow14",
			"name": "SequenceFlow14",
			"sourceRef": "73bbe769-0a83-436f-9215-30652c1f12bf",
			"targetRef": "d0a19dc4-2bff-4aec-8df2-ae448a813a4c"
		},
		"1fc7930a-f1e3-41af-82c5-47df299f644e": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow15",
			"name": "SequenceFlow15",
			"sourceRef": "44394e5c-f258-4fbc-93f2-8aa58fced2ff",
			"targetRef": "fe3536db-e35c-48b5-a95a-adb681305bb6"
		},
		"ebcab00a-e8a2-4d2d-90f1-477435dd7b65": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow16",
			"name": "SequenceFlow16",
			"sourceRef": "2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe",
			"targetRef": "ef79d601-5202-4e1a-8f85-d6587bcf76f8"
		},
		"f5b5e72b-b861-4bcf-98c6-6dfa43f427b0": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow18",
			"name": "SequenceFlow18",
			"sourceRef": "83a581ee-5c56-468a-8569-e5a363bfd845",
			"targetRef": "73bbe769-0a83-436f-9215-30652c1f12bf"
		},
		"50d8a7c9-6e9f-4985-beb4-792fb1aa764d": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow19",
			"name": "SequenceFlow19",
			"sourceRef": "c5ddc870-b40f-4900-bd00-098dc4a32147",
			"targetRef": "2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe"
		},
		"9f230ccd-62a4-4871-89b5-1a55c57a1a62": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow20",
			"name": "SequenceFlow20",
			"sourceRef": "431a99cf-4140-4e15-9de7-cd59092faa23",
			"targetRef": "44394e5c-f258-4fbc-93f2-8aa58fced2ff"
		},
		"941ef3e9-90a6-40d0-af82-f5e53d56005d": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow21",
			"name": "SequenceFlow21",
			"sourceRef": "d0a19dc4-2bff-4aec-8df2-ae448a813a4c",
			"targetRef": "8245702a-ce8d-461e-865a-dbb2c2f6759e"
		},
		"6a481d95-905a-4996-aedc-600ca25f0831": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow22",
			"name": "SequenceFlow22",
			"sourceRef": "8245702a-ce8d-461e-865a-dbb2c2f6759e",
			"targetRef": "4e440c6f-aeeb-45bd-b412-e025264ea073"
		},
		"42fa7a2d-c526-4a02-b3ba-49b5168ba644": {
			"classDefinition": "com.sap.bpm.wfs.ui.Diagram",
			"symbols": {
				"df898b52-91e1-4778-baad-2ad9a261d30e": {},
				"53e54950-7757-4161-82c9-afa7e86cff2c": {},
				"6bb141da-d485-4317-93b8-e17711df4c32": {},
				"0a981c25-2612-4016-b063-b2a1a35abd77": {},
				"57af5044-4692-48fa-a448-09b8028a6f67": {},
				"b3dbb845-eb46-4e0f-8684-bf2355a511ab": {},
				"67def05f-7674-4de7-b523-e0431e4bf766": {},
				"8c7668cc-3abf-4526-a481-4554183dd5e9": {},
				"2b8da3a3-5f57-4807-b715-cebb8dc0ae6b": {},
				"377cbb2b-aa26-493d-89a7-49bf99529d7b": {},
				"f511b23f-49ed-45b6-ab95-c4705e584676": {},
				"4485eb61-4e86-4862-8005-aaf3c36bf01f": {},
				"e2d2d3b6-fbbb-416b-b817-28e3adb9095e": {},
				"ca894802-ef63-44ca-87cf-80dd5421c1b5": {},
				"91fd4525-1cd6-471f-a14b-3974df4be944": {},
				"20870c8a-c53a-42bf-bf0c-a8bac28469b5": {},
				"eb1ee4b8-d4e8-488c-be1c-0b2570b7ac17": {},
				"f4551b95-a4e1-419e-b3d3-438210112627": {},
				"977922e7-28e2-405c-af21-9ad16f1b408b": {},
				"2f93c7d5-be4b-4722-b02b-28713715daa2": {},
				"9851e848-de7f-4b38-96e8-577cca9716ef": {},
				"b9a91cd5-e099-4683-89d6-e8183a6d20c8": {},
				"e9b9e70d-e135-4ec2-8540-98fafcd5988e": {},
				"f05059fb-52b0-47cb-b4b3-549a9cd7b0fe": {},
				"bae9def8-11af-4c44-86e9-baf7702aee64": {},
				"949ca528-2fe9-45a2-9290-da3701d7df49": {},
				"c2768f0d-576c-4aac-96bb-a22e1959da1f": {},
				"8b145b39-93b5-4b55-830b-d08804cfd122": {},
				"b486136f-1018-479d-8a80-696b0af7a66a": {},
				"648fba0f-e90b-4fc2-a9f4-49d26e007fee": {},
				"f6f37fb1-aa80-4c39-ac03-bbd3cdddd0b7": {},
				"72ba718d-8214-44b2-8eea-730753e23aa9": {},
				"aedc2ed1-1376-42e2-b32f-cd643e0554c0": {},
				"79770262-293b-40b2-a586-599a79d216c5": {},
				"20368cea-524a-488d-be65-adbd887bfdb7": {},
				"5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014": {},
				"b82912b5-4926-4cd8-9040-2b2e21dbb111": {},
				"df958d08-a17b-463a-86c0-79259b04cfe0": {},
				"bf315ba2-2a0d-4a9b-865b-9f60062fef66": {}
			}
		},
		"df898b52-91e1-4778-baad-2ad9a261d30e": {
			"classDefinition": "com.sap.bpm.wfs.ui.StartEventSymbol",
			"x": -92,
			"y": 26,
			"width": 32,
			"height": 32,
			"object": "11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3"
		},
		"53e54950-7757-4161-82c9-afa7e86cff2c": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 916,
			"y": 18.5,
			"width": 35,
			"height": 35,
			"object": "2798f4e7-bc42-4fad-a248-159095a2f40a"
		},
		"6bb141da-d485-4317-93b8-e17711df4c32": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "-76,40 136,40",
			"sourceSymbol": "df898b52-91e1-4778-baad-2ad9a261d30e",
			"targetSymbol": "0a981c25-2612-4016-b063-b2a1a35abd77",
			"object": "c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f"
		},
		"0a981c25-2612-4016-b063-b2a1a35abd77": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 115,
			"y": 17,
			"object": "78ed913a-e099-489b-ad32-087b7b175485"
		},
		"57af5044-4692-48fa-a448-09b8028a6f67": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "136,38 136,102 122,102 122,175",
			"sourceSymbol": "0a981c25-2612-4016-b063-b2a1a35abd77",
			"targetSymbol": "648fba0f-e90b-4fc2-a9f4-49d26e007fee",
			"object": "70f95383-dca6-49f3-a014-8db5e210e426"
		},
		"b3dbb845-eb46-4e0f-8684-bf2355a511ab": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 221,
			"y": 12,
			"width": 100,
			"height": 60,
			"object": "c4fd6e88-ee6a-46fe-a35c-f72872ee6cf0"
		},
		"67def05f-7674-4de7-b523-e0431e4bf766": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "271,40 381,40",
			"sourceSymbol": "b3dbb845-eb46-4e0f-8684-bf2355a511ab",
			"targetSymbol": "377cbb2b-aa26-493d-89a7-49bf99529d7b",
			"object": "498c696d-21d0-44ab-abca-1b08548a4d3b"
		},
		"8c7668cc-3abf-4526-a481-4554183dd5e9": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 64.75,
			"y": 228,
			"width": 100,
			"height": 60,
			"object": "73bbe769-0a83-436f-9215-30652c1f12bf"
		},
		"2b8da3a3-5f57-4807-b715-cebb8dc0ae6b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "152,41 254,41",
			"sourceSymbol": "0a981c25-2612-4016-b063-b2a1a35abd77",
			"targetSymbol": "b3dbb845-eb46-4e0f-8684-bf2355a511ab",
			"object": "4959ca1d-5030-4dc5-b509-1830778d8369"
		},
		"377cbb2b-aa26-493d-89a7-49bf99529d7b": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 360,
			"y": 17,
			"object": "e4b655e9-f50a-4256-9e31-bf67147ffebd"
		},
		"f511b23f-49ed-45b6-ab95-c4705e584676": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "381,40 481,40",
			"sourceSymbol": "377cbb2b-aa26-493d-89a7-49bf99529d7b",
			"targetSymbol": "ca894802-ef63-44ca-87cf-80dd5421c1b5",
			"object": "3dff84db-b2a0-4a23-8324-72cc5cadb1c6"
		},
		"4485eb61-4e86-4862-8005-aaf3c36bf01f": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 363,
			"y": 371.5,
			"width": 35,
			"height": 35,
			"object": "afbd3f14-2dfd-44f3-8194-931fff9ad523"
		},
		"e2d2d3b6-fbbb-416b-b817-28e3adb9095e": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "380.75,38 380.75,389",
			"sourceSymbol": "377cbb2b-aa26-493d-89a7-49bf99529d7b",
			"targetSymbol": "4485eb61-4e86-4862-8005-aaf3c36bf01f",
			"object": "ba86078b-35d8-4898-88d5-143541962920"
		},
		"ca894802-ef63-44ca-87cf-80dd5421c1b5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 460,
			"y": 21,
			"object": "d4192e6b-7df5-431e-ae8a-7b1546e5a157"
		},
		"91fd4525-1cd6-471f-a14b-3974df4be944": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "481,42 481,150",
			"sourceSymbol": "ca894802-ef63-44ca-87cf-80dd5421c1b5",
			"targetSymbol": "72ba718d-8214-44b2-8eea-730753e23aa9",
			"object": "90abf7bd-93f2-4803-8075-e047a9e67d9c"
		},
		"20870c8a-c53a-42bf-bf0c-a8bac28469b5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 430.5,
			"y": 228,
			"width": 100,
			"height": 60,
			"object": "2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe"
		},
		"eb1ee4b8-d4e8-488c-be1c-0b2570b7ac17": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "481,40 644,40",
			"sourceSymbol": "ca894802-ef63-44ca-87cf-80dd5421c1b5",
			"targetSymbol": "f4551b95-a4e1-419e-b3d3-438210112627",
			"object": "7199593d-4ed2-48b4-9422-20ea7dd1c8bd"
		},
		"f4551b95-a4e1-419e-b3d3-438210112627": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 594,
			"y": 8,
			"width": 100,
			"height": 60,
			"object": "0cdd5704-7705-44d5-a768-7177a671987e"
		},
		"977922e7-28e2-405c-af21-9ad16f1b408b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "644,35.5 775,35.5",
			"sourceSymbol": "f4551b95-a4e1-419e-b3d3-438210112627",
			"targetSymbol": "9851e848-de7f-4b38-96e8-577cca9716ef",
			"object": "d90be860-76c5-44f8-9c55-d6c4a42e65e9"
		},
		"2f93c7d5-be4b-4722-b02b-28713715daa2": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 714,
			"y": 228,
			"width": 100,
			"height": 60,
			"object": "44394e5c-f258-4fbc-93f2-8aa58fced2ff"
		},
		"9851e848-de7f-4b38-96e8-577cca9716ef": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 754,
			"y": 12,
			"object": "f93e58ba-2448-471c-8b69-150159cfa460"
		},
		"b9a91cd5-e099-4683-89d6-e8183a6d20c8": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "775,34.5 933.5,34.5",
			"sourceSymbol": "9851e848-de7f-4b38-96e8-577cca9716ef",
			"targetSymbol": "53e54950-7757-4161-82c9-afa7e86cff2c",
			"object": "98f9da79-aab4-41e3-be03-7a954567e845"
		},
		"e9b9e70d-e135-4ec2-8540-98fafcd5988e": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "775,33 775,150",
			"sourceSymbol": "9851e848-de7f-4b38-96e8-577cca9716ef",
			"targetSymbol": "79770262-293b-40b2-a586-599a79d216c5",
			"object": "4dc8e57a-eabf-4972-a969-31b46b83e32e"
		},
		"f05059fb-52b0-47cb-b4b3-549a9cd7b0fe": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 97.5,
			"y": 564.5,
			"width": 35,
			"height": 35,
			"object": "4e440c6f-aeeb-45bd-b412-e025264ea073"
		},
		"bae9def8-11af-4c44-86e9-baf7702aee64": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "114.875,258 114.875,329",
			"sourceSymbol": "8c7668cc-3abf-4526-a481-4554183dd5e9",
			"targetSymbol": "5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014",
			"object": "3c7f7ba0-53db-4ac3-85e2-c4d87277d50e"
		},
		"949ca528-2fe9-45a2-9290-da3701d7df49": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 864,
			"y": 240.5,
			"width": 35,
			"height": 35,
			"object": "fe3536db-e35c-48b5-a95a-adb681305bb6"
		},
		"c2768f0d-576c-4aac-96bb-a22e1959da1f": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "764,258 881.5,258",
			"sourceSymbol": "2f93c7d5-be4b-4722-b02b-28713715daa2",
			"targetSymbol": "949ca528-2fe9-45a2-9290-da3701d7df49",
			"object": "1fc7930a-f1e3-41af-82c5-47df299f644e"
		},
		"8b145b39-93b5-4b55-830b-d08804cfd122": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 580.5,
			"y": 240.5,
			"width": 35,
			"height": 35,
			"object": "ef79d601-5202-4e1a-8f85-d6587bcf76f8"
		},
		"b486136f-1018-479d-8a80-696b0af7a66a": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "480.5,258 598,258",
			"sourceSymbol": "20870c8a-c53a-42bf-bf0c-a8bac28469b5",
			"targetSymbol": "8b145b39-93b5-4b55-830b-d08804cfd122",
			"object": "ebcab00a-e8a2-4d2d-90f1-477435dd7b65"
		},
		"648fba0f-e90b-4fc2-a9f4-49d26e007fee": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 72,
			"y": 145,
			"width": 100,
			"height": 60,
			"object": "83a581ee-5c56-468a-8569-e5a363bfd845"
		},
		"f6f37fb1-aa80-4c39-ac03-bbd3cdddd0b7": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "118.375,175 118.375,258",
			"sourceSymbol": "648fba0f-e90b-4fc2-a9f4-49d26e007fee",
			"targetSymbol": "8c7668cc-3abf-4526-a481-4554183dd5e9",
			"object": "f5b5e72b-b861-4bcf-98c6-6dfa43f427b0"
		},
		"72ba718d-8214-44b2-8eea-730753e23aa9": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 431,
			"y": 120,
			"width": 100,
			"height": 60,
			"object": "c5ddc870-b40f-4900-bd00-098dc4a32147"
		},
		"aedc2ed1-1376-42e2-b32f-cd643e0554c0": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "480.75,150 480.75,258",
			"sourceSymbol": "72ba718d-8214-44b2-8eea-730753e23aa9",
			"targetSymbol": "20870c8a-c53a-42bf-bf0c-a8bac28469b5",
			"object": "50d8a7c9-6e9f-4985-beb4-792fb1aa764d"
		},
		"79770262-293b-40b2-a586-599a79d216c5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 725,
			"y": 120,
			"width": 100,
			"height": 60,
			"object": "431a99cf-4140-4e15-9de7-cd59092faa23"
		},
		"20368cea-524a-488d-be65-adbd887bfdb7": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "775,150 775,204.25 764,204.25 764,258",
			"sourceSymbol": "79770262-293b-40b2-a586-599a79d216c5",
			"targetSymbol": "2f93c7d5-be4b-4722-b02b-28713715daa2",
			"object": "9f230ccd-62a4-4871-89b5-1a55c57a1a62"
		},
		"5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 65,
			"y": 299,
			"width": 100,
			"height": 60,
			"object": "d0a19dc4-2bff-4aec-8df2-ae448a813a4c"
		},
		"b82912b5-4926-4cd8-9040-2b2e21dbb111": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "115,329 115,437",
			"sourceSymbol": "5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014",
			"targetSymbol": "df958d08-a17b-463a-86c0-79259b04cfe0",
			"object": "941ef3e9-90a6-40d0-af82-f5e53d56005d"
		},
		"df958d08-a17b-463a-86c0-79259b04cfe0": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 65,
			"y": 407,
			"width": 100,
			"height": 60,
			"object": "8245702a-ce8d-461e-865a-dbb2c2f6759e"
		},
		"bf315ba2-2a0d-4a9b-865b-9f60062fef66": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "115,437 115,582",
			"sourceSymbol": "df958d08-a17b-463a-86c0-79259b04cfe0",
			"targetSymbol": "f05059fb-52b0-47cb-b4b3-549a9cd7b0fe",
			"object": "6a481d95-905a-4996-aedc-600ca25f0831"
		},
		"62d7f4ed-4063-4c44-af8b-39050bd44926": {
			"classDefinition": "com.sap.bpm.wfs.LastIDs",
			"sequenceflow": 22,
			"startevent": 1,
			"endevent": 6,
			"usertask": 2,
			"servicetask": 4,
			"scripttask": 6,
			"exclusivegateway": 4
		}
	}
}