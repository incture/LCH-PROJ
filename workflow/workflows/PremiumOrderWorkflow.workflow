{
	"contents": {
		"96e5fbd8-10f0-4cfd-8eb9-e6dc73402888": {
			"classDefinition": "com.sap.bpm.wfs.Model",
			"id": "premiumorderworkflow",
			"subject": "PremiumOrderWorkflow",
			"name": "PremiumOrderWorkflow",
			"documentation": "Premium Order Workflow Description",
			"lastIds": "62d7f4ed-4063-4c44-af8b-39050bd44926",
			"events": {
				"11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3": {
					"name": "StartEvent1"
				},
				"2798f4e7-bc42-4fad-a248-159095a2f40a": {
					"name": "EndEvent1"
				}
			},
			"activities": {
				"ee6e7b60-2913-469d-9236-38403b04cb00": {
					"name": "Approver Task"
				},
				"5473944c-625e-4932-8f8b-a02bd3bb711d": {
					"name": "Is Approved"
				},
				"9b98c391-3c6a-45f4-8e10-04c49036ad36": {
					"name": "ServiceTask1"
				},
				"4740336e-3648-47b0-b40d-d929628b424d": {
					"name": "Accountant Task"
				},
				"207903dc-2994-4fc2-97e0-c8b27cf71d73": {
					"name": "ScriptTask1"
				},
				"1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab": {
					"name": "ScriptTask2"
				},
				"1422dd9d-9fd2-4838-988c-64fbfdd423ab": {
					"name": "ServiceTask2"
				},
				"5b1f92fd-79a3-472d-8b4e-98e210516300": {
					"name": "ScriptTask3"
				},
				"cfbbfe63-8c16-4b3d-81f5-3c8779f61bc5": {
					"name": "ExclusiveGateway2"
				}
			},
			"sequenceFlows": {
				"c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f": {
					"name": "SequenceFlow1"
				},
				"6ff85cd6-a99c-41e1-bc14-dc1b3dc621e5": {
					"name": "SequenceFlow2"
				},
				"0ae95448-4949-4ea1-aa4d-442156bea302": {
					"name": "Approved"
				},
				"5f51d6ca-c93d-4096-98a7-e87cfec86773": {
					"name": "SequenceFlow4"
				},
				"73dc266c-4023-4683-a5bf-f11aaed73345": {
					"name": "SequenceFlow5"
				},
				"61561ef2-740f-4eba-9362-c0fe3519f7f5": {
					"name": "SequenceFlow6"
				},
				"46f597fc-2055-4088-8e46-8a2c3aa2270c": {
					"name": "SequenceFlow8"
				},
				"c8061712-316e-43ab-ad05-920324c507d4": {
					"name": "Rejected"
				},
				"75b09c3b-2d98-49ed-a479-791eda3afe90": {
					"name": "SequenceFlow11"
				},
				"870a4a6e-5ba4-47ab-8f79-7a6cd27804c3": {
					"name": "SequenceFlow12"
				},
				"3b309d9f-c198-4b7e-905d-001cedd0063e": {
					"name": "SequenceFlow13"
				},
				"ac52fde4-acdd-455d-8ff4-0a45554b80a2": {
					"name": "SequenceFlow14"
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
		"ee6e7b60-2913-469d-9236-38403b04cb00": {
			"classDefinition": "com.sap.bpm.wfs.UserTask",
			"subject": "Premium Order Approver Task",
			"priority": "MEDIUM",
			"isHiddenInLogForParticipant": false,
			"supportsForward": false,
			"userInterface": "sapui5://comsapbpmworkflow.comsapbpmwusformplayer/com.sap.bpm.wus.form.player",
			"recipientUsers": "${context.pendingWithApprover}",
			"formReference": "/forms/PremiumOrderWorkflow/PremiumOrderForm.form",
			"userInterfaceParams": [{
				"key": "formId",
				"value": "premiumorderform"
			}, {
				"key": "formRevision",
				"value": "1.0"
			}],
			"id": "usertask1",
			"name": "Approver Task"
		},
		"5473944c-625e-4932-8f8b-a02bd3bb711d": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway1",
			"name": "Is Approved",
			"default": "c8061712-316e-43ab-ad05-920324c507d4"
		},
		"9b98c391-3c6a-45f4-8e10-04c49036ad36": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/premium/updateTableDetails",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowRequest}",
			"responseVariable": "${context.workflowResponse}",
			"id": "servicetask1",
			"name": "ServiceTask1"
		},
		"4740336e-3648-47b0-b40d-d929628b424d": {
			"classDefinition": "com.sap.bpm.wfs.UserTask",
			"subject": "Premium Order Accountant task",
			"priority": "MEDIUM",
			"isHiddenInLogForParticipant": false,
			"supportsForward": false,
			"userInterface": "sapui5://comsapbpmworkflow.comsapbpmwusformplayer/com.sap.bpm.wus.form.player",
			"recipientUsers": "${context.pendingWithAccountant}",
			"formReference": "/forms/PremiumOrderWorkflow/PremiumOrderForm.form",
			"userInterfaceParams": [{
				"key": "formId",
				"value": "premiumorderform"
			}, {
				"key": "formRevision",
				"value": "1.0"
			}],
			"id": "usertask2",
			"name": "Accountant Task"
		},
		"207903dc-2994-4fc2-97e0-c8b27cf71d73": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/PremiumOrderWorkflow/workflowInput.js",
			"id": "scripttask1",
			"name": "ScriptTask1"
		},
		"1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/PremiumOrderWorkflow/workflowInfoInput2.js",
			"id": "scripttask2",
			"name": "ScriptTask2"
		},
		"1422dd9d-9fd2-4838-988c-64fbfdd423ab": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/premium/updateTableDetails",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowRequest}",
			"responseVariable": "${context.worflowResponse2}",
			"id": "servicetask2",
			"name": "ServiceTask2"
		},
		"5b1f92fd-79a3-472d-8b4e-98e210516300": {
			"classDefinition": "com.sap.bpm.wfs.ScriptTask",
			"reference": "/scripts/PremiumOrderWorkflow/workflowInput.js",
			"id": "scripttask3",
			"name": "ScriptTask3"
		},
		"cfbbfe63-8c16-4b3d-81f5-3c8779f61bc5": {
			"classDefinition": "com.sap.bpm.wfs.ExclusiveGateway",
			"id": "exclusivegateway2",
			"name": "ExclusiveGateway2"
		},
		"c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow1",
			"name": "SequenceFlow1",
			"sourceRef": "11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3",
			"targetRef": "ee6e7b60-2913-469d-9236-38403b04cb00"
		},
		"6ff85cd6-a99c-41e1-bc14-dc1b3dc621e5": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow2",
			"name": "SequenceFlow2",
			"sourceRef": "ee6e7b60-2913-469d-9236-38403b04cb00",
			"targetRef": "5473944c-625e-4932-8f8b-a02bd3bb711d"
		},
		"0ae95448-4949-4ea1-aa4d-442156bea302": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"condition": "${context.managerActionType=='Approved'}",
			"id": "sequenceflow3",
			"name": "Approved",
			"sourceRef": "5473944c-625e-4932-8f8b-a02bd3bb711d",
			"targetRef": "207903dc-2994-4fc2-97e0-c8b27cf71d73"
		},
		"5f51d6ca-c93d-4096-98a7-e87cfec86773": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow4",
			"name": "SequenceFlow4",
			"sourceRef": "9b98c391-3c6a-45f4-8e10-04c49036ad36",
			"targetRef": "4740336e-3648-47b0-b40d-d929628b424d"
		},
		"73dc266c-4023-4683-a5bf-f11aaed73345": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow5",
			"name": "SequenceFlow5",
			"sourceRef": "4740336e-3648-47b0-b40d-d929628b424d",
			"targetRef": "cfbbfe63-8c16-4b3d-81f5-3c8779f61bc5"
		},
		"61561ef2-740f-4eba-9362-c0fe3519f7f5": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow6",
			"name": "SequenceFlow6",
			"sourceRef": "207903dc-2994-4fc2-97e0-c8b27cf71d73",
			"targetRef": "9b98c391-3c6a-45f4-8e10-04c49036ad36"
		},
		"46f597fc-2055-4088-8e46-8a2c3aa2270c": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow8",
			"name": "SequenceFlow8",
			"sourceRef": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab",
			"targetRef": "9b98c391-3c6a-45f4-8e10-04c49036ad36"
		},
		"c8061712-316e-43ab-ad05-920324c507d4": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow10",
			"name": "Rejected",
			"sourceRef": "5473944c-625e-4932-8f8b-a02bd3bb711d",
			"targetRef": "5b1f92fd-79a3-472d-8b4e-98e210516300"
		},
		"75b09c3b-2d98-49ed-a479-791eda3afe90": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow11",
			"name": "SequenceFlow11",
			"sourceRef": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab",
			"targetRef": "1422dd9d-9fd2-4838-988c-64fbfdd423ab"
		},
		"870a4a6e-5ba4-47ab-8f79-7a6cd27804c3": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow12",
			"name": "SequenceFlow12",
			"sourceRef": "1422dd9d-9fd2-4838-988c-64fbfdd423ab",
			"targetRef": "2798f4e7-bc42-4fad-a248-159095a2f40a"
		},
		"3b309d9f-c198-4b7e-905d-001cedd0063e": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow13",
			"name": "SequenceFlow13",
			"sourceRef": "5b1f92fd-79a3-472d-8b4e-98e210516300",
			"targetRef": "1422dd9d-9fd2-4838-988c-64fbfdd423ab"
		},
		"ac52fde4-acdd-455d-8ff4-0a45554b80a2": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow14",
			"name": "SequenceFlow14",
			"sourceRef": "cfbbfe63-8c16-4b3d-81f5-3c8779f61bc5",
			"targetRef": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab"
		},
		"42fa7a2d-c526-4a02-b3ba-49b5168ba644": {
			"classDefinition": "com.sap.bpm.wfs.ui.Diagram",
			"symbols": {
				"df898b52-91e1-4778-baad-2ad9a261d30e": {},
				"53e54950-7757-4161-82c9-afa7e86cff2c": {},
				"6bb141da-d485-4317-93b8-e17711df4c32": {},
				"8ce074b8-5b22-4821-9c7a-5d6222f51938": {},
				"86785bb3-7a4d-4c0c-89a3-36441ce2330b": {},
				"99496cae-e05d-4b21-93a6-e69fb4c063fd": {},
				"0ba550bf-68cd-4e49-b35c-12ac9ce15043": {},
				"1cb3df47-0b8d-43fb-8cf5-e7d77f5d344e": {},
				"c2d6a56f-001a-4f8b-baff-7458ae4e9f76": {},
				"4c7b7c3f-755c-4227-b757-273305a83d8d": {},
				"e6215272-57b1-47ba-b2e4-75b66d61ef4b": {},
				"7d204c03-1d43-4036-805d-44c152a973df": {},
				"dbe35272-7a7c-45e9-8c5c-02bfe1063579": {},
				"8bda4aa6-e6bb-46b0-9c60-c1ee7530043f": {},
				"366aa3ee-79f9-4782-8f90-42974be2bd12": {},
				"d8614466-5275-4ac0-8377-d2abfbc02bbb": {},
				"cb81d354-03c2-42a5-8786-5b4b97419222": {},
				"8447ab23-c7a8-4980-a520-76425d4877fb": {},
				"63a93f9e-e147-42c4-9eab-27ba35efd724": {},
				"ee5b2d1f-6f87-4228-8be3-6af537c9d977": {},
				"13e1a5a2-9fae-4363-895f-e78a9d0b7282": {},
				"7af02123-f91e-4f3c-b050-ec5ca9fea607": {}
			}
		},
		"df898b52-91e1-4778-baad-2ad9a261d30e": {
			"classDefinition": "com.sap.bpm.wfs.ui.StartEventSymbol",
			"x": 100,
			"y": 100,
			"width": 32,
			"height": 32,
			"object": "11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3"
		},
		"53e54950-7757-4161-82c9-afa7e86cff2c": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 541,
			"y": 369,
			"width": 35,
			"height": 35,
			"object": "2798f4e7-bc42-4fad-a248-159095a2f40a"
		},
		"6bb141da-d485-4317-93b8-e17711df4c32": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "116,116 236,116",
			"sourceSymbol": "df898b52-91e1-4778-baad-2ad9a261d30e",
			"targetSymbol": "8ce074b8-5b22-4821-9c7a-5d6222f51938",
			"object": "c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f"
		},
		"8ce074b8-5b22-4821-9c7a-5d6222f51938": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 186,
			"y": 86,
			"width": 100,
			"height": 60,
			"object": "ee6e7b60-2913-469d-9236-38403b04cb00"
		},
		"86785bb3-7a4d-4c0c-89a3-36441ce2330b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "236,118.5 346,118.5",
			"sourceSymbol": "8ce074b8-5b22-4821-9c7a-5d6222f51938",
			"targetSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"object": "6ff85cd6-a99c-41e1-bc14-dc1b3dc621e5"
		},
		"99496cae-e05d-4b21-93a6-e69fb4c063fd": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 325,
			"y": 100,
			"object": "5473944c-625e-4932-8f8b-a02bd3bb711d"
		},
		"0ba550bf-68cd-4e49-b35c-12ac9ce15043": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "346,118.5 491,118.5",
			"sourceSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"targetSymbol": "7d204c03-1d43-4036-805d-44c152a973df",
			"object": "0ae95448-4949-4ea1-aa4d-442156bea302"
		},
		"1cb3df47-0b8d-43fb-8cf5-e7d77f5d344e": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 573,
			"y": 86,
			"width": 100,
			"height": 60,
			"object": "9b98c391-3c6a-45f4-8e10-04c49036ad36"
		},
		"c2d6a56f-001a-4f8b-baff-7458ae4e9f76": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "623,116 759,116",
			"sourceSymbol": "1cb3df47-0b8d-43fb-8cf5-e7d77f5d344e",
			"targetSymbol": "4c7b7c3f-755c-4227-b757-273305a83d8d",
			"object": "5f51d6ca-c93d-4096-98a7-e87cfec86773"
		},
		"4c7b7c3f-755c-4227-b757-273305a83d8d": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 709,
			"y": 86,
			"width": 100,
			"height": 60,
			"object": "4740336e-3648-47b0-b40d-d929628b424d"
		},
		"e6215272-57b1-47ba-b2e4-75b66d61ef4b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "759,116 993,116",
			"sourceSymbol": "4c7b7c3f-755c-4227-b757-273305a83d8d",
			"targetSymbol": "13e1a5a2-9fae-4363-895f-e78a9d0b7282",
			"object": "73dc266c-4023-4683-a5bf-f11aaed73345"
		},
		"7d204c03-1d43-4036-805d-44c152a973df": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 441,
			"y": 86,
			"width": 100,
			"height": 60,
			"object": "207903dc-2994-4fc2-97e0-c8b27cf71d73"
		},
		"dbe35272-7a7c-45e9-8c5c-02bfe1063579": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "491,116 623,116",
			"sourceSymbol": "7d204c03-1d43-4036-805d-44c152a973df",
			"targetSymbol": "1cb3df47-0b8d-43fb-8cf5-e7d77f5d344e",
			"object": "61561ef2-740f-4eba-9362-c0fe3519f7f5"
		},
		"8bda4aa6-e6bb-46b0-9c60-c1ee7530043f": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 774,
			"y": 172,
			"width": 100,
			"height": 60,
			"object": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab"
		},
		"366aa3ee-79f9-4782-8f90-42974be2bd12": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 509,
			"y": 300,
			"width": 100,
			"height": 60,
			"object": "1422dd9d-9fd2-4838-988c-64fbfdd423ab"
		},
		"d8614466-5275-4ac0-8377-d2abfbc02bbb": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "346,121 346,262",
			"sourceSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"targetSymbol": "63a93f9e-e147-42c4-9eab-27ba35efd724",
			"object": "c8061712-316e-43ab-ad05-920324c507d4"
		},
		"cb81d354-03c2-42a5-8786-5b4b97419222": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "824,202 824,328.5 588,328.5",
			"sourceSymbol": "8bda4aa6-e6bb-46b0-9c60-c1ee7530043f",
			"targetSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"object": "75b09c3b-2d98-49ed-a479-791eda3afe90"
		},
		"8447ab23-c7a8-4980-a520-76425d4877fb": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "558.25,339 558.25,386.5",
			"sourceSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"targetSymbol": "53e54950-7757-4161-82c9-afa7e86cff2c",
			"object": "870a4a6e-5ba4-47ab-8f79-7a6cd27804c3"
		},
		"63a93f9e-e147-42c4-9eab-27ba35efd724": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 296,
			"y": 232,
			"width": 100,
			"height": 60,
			"object": "5b1f92fd-79a3-472d-8b4e-98e210516300"
		},
		"ee5b2d1f-6f87-4228-8be3-6af537c9d977": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "346,262 346,331 559,331",
			"sourceSymbol": "63a93f9e-e147-42c4-9eab-27ba35efd724",
			"targetSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"object": "3b309d9f-c198-4b7e-905d-001cedd0063e"
		},
		"13e1a5a2-9fae-4363-895f-e78a9d0b7282": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 972,
			"y": 95,
			"object": "cfbbfe63-8c16-4b3d-81f5-3c8779f61bc5"
		},
		"7af02123-f91e-4f3c-b050-ec5ca9fea607": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "992,116 992,202 824,202",
			"sourceSymbol": "13e1a5a2-9fae-4363-895f-e78a9d0b7282",
			"targetSymbol": "8bda4aa6-e6bb-46b0-9c60-c1ee7530043f",
			"object": "ac52fde4-acdd-455d-8ff4-0a45554b80a2"
		},
		"62d7f4ed-4063-4c44-af8b-39050bd44926": {
			"classDefinition": "com.sap.bpm.wfs.LastIDs",
			"sequenceflow": 14,
			"startevent": 1,
			"endevent": 1,
			"usertask": 2,
			"servicetask": 2,
			"scripttask": 3,
			"exclusivegateway": 2
		},
		"31505c8d-df9b-47a6-b72b-835cac313468": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "909,112 791,112 791,104 623,104",
			"sourceSymbol": "8bda4aa6-e6bb-46b0-9c60-c1ee7530043f",
			"targetSymbol": "1cb3df47-0b8d-43fb-8cf5-e7d77f5d344e",
			"object": "46f597fc-2055-4088-8e46-8a2c3aa2270c"
		}
	}
}