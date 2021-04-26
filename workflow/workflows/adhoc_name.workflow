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
				},
				"e275d061-44c4-464a-9996-ba8c4d6e6fe4": {
					"name": "ServiceTask5"
				},
				"d16ed1c0-6476-4020-9bdd-adea35dc990b": {
					"name": "ScriptTask7"
				},
				"b336c47d-e845-46bd-977d-3749a8682817": {
					"name": "ScriptTask8"
				},
				"02ce0894-70eb-4a1f-83b7-24ee4f7b838b": {
					"name": "ServiceTask7"
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
				"ebcab00a-e8a2-4d2d-90f1-477435dd7b65": {
					"name": "SequenceFlow16"
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
				},
				"f6d5b735-24a1-4905-aa08-508d79cd1b68": {
					"name": "SequenceFlow23"
				},
				"11dac000-074c-483a-b12f-aa1c66a48cc9": {
					"name": "SequenceFlow24"
				},
				"e0396a14-7ba5-44c3-a8a8-15119b1ccdd7": {
					"name": "SequenceFlow25"
				},
				"879c4b34-8ad2-4d05-b42b-f39e7ca8af8b": {
					"name": "SequenceFlow26"
				},
				"9e98274e-15a3-4df1-86ea-d2140aed9739": {
					"name": "SequenceFlow28"
				},
				"b05c245d-5a4f-491a-be22-040c5365c408": {
					"name": "SequenceFlow29"
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
			"path": "/adhocorders/updateApprovalWorkflowDetails",
			"httpMethod": "POST",
			"xsrfPath": "",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "${context.responseMessage3}",
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
			"responseVariable": "${context.responseMessage}",
			"id": "servicetask3",
			"name": "ServiceTask3"
		},
		"c5ddc870-b40f-4900-bd00-098dc4a32147": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput.js",
			"id": "scripttask4",
			"name": "ScriptTask4"
		},
		"431a99cf-4140-4e15-9de7-cd59092faa23": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput2.js",
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
			"path": "/adhocorders/updateApprovalWorkflowDetailsForType4",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "${context.responseMessage2}",
			"headers": [],
			"id": "servicetask4",
			"name": "ServiceTask4"
		},
		"e275d061-44c4-464a-9996-ba8c4d6e6fe4": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/adhocorders/updateApprovalWorkflowDetails",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "${context.responseMessage4}",
			"id": "servicetask5",
			"name": "ServiceTask5"
		},
		"d16ed1c0-6476-4020-9bdd-adea35dc990b": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/tokenMessage.js",
			"id": "scripttask7",
			"name": "ScriptTask7"
		},
		"b336c47d-e845-46bd-977d-3749a8682817": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/adhoc_name/ServiceTaskInput3.js",
			"id": "scripttask8",
			"name": "ScriptTask8"
		},
		"02ce0894-70eb-4a1f-83b7-24ee4f7b838b": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/adhocorders/updateApprovalWorkflowDetails1",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowInfo}",
			"responseVariable": "${context.responseMessage5}",
			"id": "servicetask7",
			"name": "ServiceTask7"
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
			"targetRef": "d0a19dc4-2bff-4aec-8df2-ae448a813a4c"
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
			"targetRef": "d16ed1c0-6476-4020-9bdd-adea35dc990b"
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
			"targetRef": "b336c47d-e845-46bd-977d-3749a8682817"
		},
		"ebcab00a-e8a2-4d2d-90f1-477435dd7b65": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow16",
			"name": "SequenceFlow16",
			"sourceRef": "2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe",
			"targetRef": "ef79d601-5202-4e1a-8f85-d6587bcf76f8"
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
		"f6d5b735-24a1-4905-aa08-508d79cd1b68": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow23",
			"name": "SequenceFlow23",
			"sourceRef": "e275d061-44c4-464a-9996-ba8c4d6e6fe4",
			"targetRef": "afbd3f14-2dfd-44f3-8194-931fff9ad523"
		},
		"11dac000-074c-483a-b12f-aa1c66a48cc9": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow24",
			"name": "SequenceFlow24",
			"sourceRef": "d16ed1c0-6476-4020-9bdd-adea35dc990b",
			"targetRef": "e275d061-44c4-464a-9996-ba8c4d6e6fe4"
		},
		"e0396a14-7ba5-44c3-a8a8-15119b1ccdd7": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow25",
			"name": "SequenceFlow25",
			"sourceRef": "0cdd5704-7705-44d5-a768-7177a671987e",
			"targetRef": "431a99cf-4140-4e15-9de7-cd59092faa23"
		},
		"879c4b34-8ad2-4d05-b42b-f39e7ca8af8b": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow26",
			"name": "SequenceFlow26",
			"sourceRef": "44394e5c-f258-4fbc-93f2-8aa58fced2ff",
			"targetRef": "fe3536db-e35c-48b5-a95a-adb681305bb6"
		},
		"9e98274e-15a3-4df1-86ea-d2140aed9739": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow28",
			"name": "SequenceFlow28",
			"sourceRef": "b336c47d-e845-46bd-977d-3749a8682817",
			"targetRef": "02ce0894-70eb-4a1f-83b7-24ee4f7b838b"
		},
		"b05c245d-5a4f-491a-be22-040c5365c408": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow29",
			"name": "SequenceFlow29",
			"sourceRef": "02ce0894-70eb-4a1f-83b7-24ee4f7b838b",
			"targetRef": "0cdd5704-7705-44d5-a768-7177a671987e"
		},
		"42fa7a2d-c526-4a02-b3ba-49b5168ba644": {
			"classDefinition": "com.sap.bpm.wfs.ui.Diagram",
			"symbols": {
				"df898b52-91e1-4778-baad-2ad9a261d30e": {},
				"6bb141da-d485-4317-93b8-e17711df4c32": {},
				"0a981c25-2612-4016-b063-b2a1a35abd77": {},
				"57af5044-4692-48fa-a448-09b8028a6f67": {},
				"b3dbb845-eb46-4e0f-8684-bf2355a511ab": {},
				"67def05f-7674-4de7-b523-e0431e4bf766": {},
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
				"2f93c7d5-be4b-4722-b02b-28713715daa2": {},
				"f05059fb-52b0-47cb-b4b3-549a9cd7b0fe": {},
				"949ca528-2fe9-45a2-9290-da3701d7df49": {},
				"8b145b39-93b5-4b55-830b-d08804cfd122": {},
				"b486136f-1018-479d-8a80-696b0af7a66a": {},
				"72ba718d-8214-44b2-8eea-730753e23aa9": {},
				"aedc2ed1-1376-42e2-b32f-cd643e0554c0": {},
				"79770262-293b-40b2-a586-599a79d216c5": {},
				"20368cea-524a-488d-be65-adbd887bfdb7": {},
				"5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014": {},
				"b82912b5-4926-4cd8-9040-2b2e21dbb111": {},
				"df958d08-a17b-463a-86c0-79259b04cfe0": {},
				"bf315ba2-2a0d-4a9b-865b-9f60062fef66": {},
				"e8daf6c7-5c4e-4335-aa53-738309787604": {},
				"1b24e5eb-3cbe-4a0a-a3fd-3779bab42c4c": {},
				"d720f067-7d30-48cb-aac8-3e98a87dab0d": {},
				"ce3990ed-5c7f-4a83-8bf1-8f463ecce950": {},
				"a661c2ef-87f7-49ab-ac1b-0493f0b83a52": {},
				"20563c92-67a2-4fd3-9f3a-35ade4723db8": {},
				"36df7493-7bfc-4db2-a6c7-d52ff1840bc5": {},
				"f4cfa9b6-eb0a-4735-ba25-e496b73a8d5d": {},
				"e686a88c-a9db-4e4d-89b7-d73e52fa56c5": {},
				"92767f34-4000-40ee-b178-9fde1056ad3b": {}
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
			"points": "139.5,38 139.5,205",
			"sourceSymbol": "0a981c25-2612-4016-b063-b2a1a35abd77",
			"targetSymbol": "5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014",
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
			"y": 525.5,
			"width": 35,
			"height": 35,
			"object": "afbd3f14-2dfd-44f3-8194-931fff9ad523"
		},
		"e2d2d3b6-fbbb-416b-b817-28e3adb9095e": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "381,38 381,327",
			"sourceSymbol": "377cbb2b-aa26-493d-89a7-49bf99529d7b",
			"targetSymbol": "d720f067-7d30-48cb-aac8-3e98a87dab0d",
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
			"y": 221,
			"width": 100,
			"height": 60,
			"object": "2f653e0a-8c1e-4a73-ba1f-34ae00d2bebe"
		},
		"eb1ee4b8-d4e8-488c-be1c-0b2570b7ac17": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "481,44.5 631,44.5",
			"sourceSymbol": "ca894802-ef63-44ca-87cf-80dd5421c1b5",
			"targetSymbol": "36df7493-7bfc-4db2-a6c7-d52ff1840bc5",
			"object": "7199593d-4ed2-48b4-9422-20ea7dd1c8bd"
		},
		"f4551b95-a4e1-419e-b3d3-438210112627": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 800,
			"y": -95,
			"width": 100,
			"height": 60,
			"object": "0cdd5704-7705-44d5-a768-7177a671987e"
		},
		"2f93c7d5-be4b-4722-b02b-28713715daa2": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 976,
			"y": 77,
			"width": 100,
			"height": 60,
			"object": "44394e5c-f258-4fbc-93f2-8aa58fced2ff"
		},
		"f05059fb-52b0-47cb-b4b3-549a9cd7b0fe": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 125.5,
			"y": 513.5,
			"width": 35,
			"height": 35,
			"object": "4e440c6f-aeeb-45bd-b412-e025264ea073"
		},
		"949ca528-2fe9-45a2-9290-da3701d7df49": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 1224,
			"y": 106.5,
			"width": 35,
			"height": 35,
			"object": "fe3536db-e35c-48b5-a95a-adb681305bb6"
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
			"points": "480.5,254.5 598,254.5",
			"sourceSymbol": "20870c8a-c53a-42bf-bf0c-a8bac28469b5",
			"targetSymbol": "8b145b39-93b5-4b55-830b-d08804cfd122",
			"object": "ebcab00a-e8a2-4d2d-90f1-477435dd7b65"
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
			"points": "480.75,150 480.75,251",
			"sourceSymbol": "72ba718d-8214-44b2-8eea-730753e23aa9",
			"targetSymbol": "20870c8a-c53a-42bf-bf0c-a8bac28469b5",
			"object": "50d8a7c9-6e9f-4985-beb4-792fb1aa764d"
		},
		"79770262-293b-40b2-a586-599a79d216c5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 976,
			"y": -89,
			"width": 100,
			"height": 60,
			"object": "431a99cf-4140-4e15-9de7-cd59092faa23"
		},
		"20368cea-524a-488d-be65-adbd887bfdb7": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "1026,-56.5 1026,104.5",
			"sourceSymbol": "79770262-293b-40b2-a586-599a79d216c5",
			"targetSymbol": "2f93c7d5-be4b-4722-b02b-28713715daa2",
			"object": "9f230ccd-62a4-4871-89b5-1a55c57a1a62"
		},
		"5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 93,
			"y": 175,
			"width": 100,
			"height": 60,
			"object": "d0a19dc4-2bff-4aec-8df2-ae448a813a4c"
		},
		"b82912b5-4926-4cd8-9040-2b2e21dbb111": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "143,205 143,342",
			"sourceSymbol": "5575a3ed-8c2f-4aa6-8ee3-46d1dd7cf014",
			"targetSymbol": "df958d08-a17b-463a-86c0-79259b04cfe0",
			"object": "941ef3e9-90a6-40d0-af82-f5e53d56005d"
		},
		"df958d08-a17b-463a-86c0-79259b04cfe0": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 93,
			"y": 312,
			"width": 100,
			"height": 60,
			"object": "8245702a-ce8d-461e-865a-dbb2c2f6759e"
		},
		"bf315ba2-2a0d-4a9b-865b-9f60062fef66": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "143,342 143,531",
			"sourceSymbol": "df958d08-a17b-463a-86c0-79259b04cfe0",
			"targetSymbol": "f05059fb-52b0-47cb-b4b3-549a9cd7b0fe",
			"object": "6a481d95-905a-4996-aedc-600ca25f0831"
		},
		"e8daf6c7-5c4e-4335-aa53-738309787604": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 331,
			"y": 391,
			"width": 100,
			"height": 60,
			"object": "e275d061-44c4-464a-9996-ba8c4d6e6fe4"
		},
		"1b24e5eb-3cbe-4a0a-a3fd-3779bab42c4c": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "380.75,421 380.75,543",
			"sourceSymbol": "e8daf6c7-5c4e-4335-aa53-738309787604",
			"targetSymbol": "4485eb61-4e86-4862-8005-aaf3c36bf01f",
			"object": "f6d5b735-24a1-4905-aa08-508d79cd1b68"
		},
		"d720f067-7d30-48cb-aac8-3e98a87dab0d": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 331,
			"y": 297,
			"width": 100,
			"height": 60,
			"object": "d16ed1c0-6476-4020-9bdd-adea35dc990b"
		},
		"ce3990ed-5c7f-4a83-8bf1-8f463ecce950": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "381,327 381,421",
			"sourceSymbol": "d720f067-7d30-48cb-aac8-3e98a87dab0d",
			"targetSymbol": "e8daf6c7-5c4e-4335-aa53-738309787604",
			"object": "11dac000-074c-483a-b12f-aa1c66a48cc9"
		},
		"a661c2ef-87f7-49ab-ac1b-0493f0b83a52": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "866,-62.5 976.5,-62.5",
			"sourceSymbol": "f4551b95-a4e1-419e-b3d3-438210112627",
			"targetSymbol": "79770262-293b-40b2-a586-599a79d216c5",
			"object": "e0396a14-7ba5-44c3-a8a8-15119b1ccdd7"
		},
		"20563c92-67a2-4fd3-9f3a-35ade4723db8": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "1075.5,115 1150.25,115 1150.25,129 1233,129",
			"sourceSymbol": "2f93c7d5-be4b-4722-b02b-28713715daa2",
			"targetSymbol": "949ca528-2fe9-45a2-9290-da3701d7df49",
			"object": "879c4b34-8ad2-4d05-b42b-f39e7ca8af8b"
		},
		"36df7493-7bfc-4db2-a6c7-d52ff1840bc5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 581,
			"y": 17,
			"width": 100,
			"height": 60,
			"object": "b336c47d-e845-46bd-977d-3749a8682817"
		},
		"f4cfa9b6-eb0a-4735-ba25-e496b73a8d5d": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "631,47 791,47",
			"sourceSymbol": "36df7493-7bfc-4db2-a6c7-d52ff1840bc5",
			"targetSymbol": "e686a88c-a9db-4e4d-89b7-d73e52fa56c5",
			"object": "9e98274e-15a3-4df1-86ea-d2140aed9739"
		},
		"e686a88c-a9db-4e4d-89b7-d73e52fa56c5": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 741,
			"y": 17,
			"width": 100,
			"height": 60,
			"object": "02ce0894-70eb-4a1f-83b7-24ee4f7b838b"
		},
		"92767f34-4000-40ee-b178-9fde1056ad3b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "791,47 791,-9.25 850,-9.25 850,-65",
			"sourceSymbol": "e686a88c-a9db-4e4d-89b7-d73e52fa56c5",
			"targetSymbol": "f4551b95-a4e1-419e-b3d3-438210112627",
			"object": "b05c245d-5a4f-491a-be22-040c5365c408"
		},
		"62d7f4ed-4063-4c44-af8b-39050bd44926": {
			"classDefinition": "com.sap.bpm.wfs.LastIDs",
			"sequenceflow": 29,
			"startevent": 1,
			"endevent": 6,
			"usertask": 2,
			"servicetask": 7,
			"scripttask": 8,
			"exclusivegateway": 4
		}
	}
}