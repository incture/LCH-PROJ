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
				"6e116996-1b13-4619-a770-ecc157994389": {
					"name": "ServiceTask1"
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
				"73dc266c-4023-4683-a5bf-f11aaed73345": {
					"name": "SequenceFlow5"
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
				"e96a133c-4c90-4e5f-b70e-b6af7a600080": {
					"name": "SequenceFlow18"
				},
				"43943115-8a35-4467-be9b-cac26564cf21": {
					"name": "SequenceFlow19"
				},
				"44fdc2cf-e5c3-4cf2-a2d5-77c5fe3539bb": {
					"name": "SequenceFlow20"
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
		"73dc266c-4023-4683-a5bf-f11aaed73345": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow5",
			"name": "SequenceFlow5",
			"sourceRef": "4740336e-3648-47b0-b40d-d929628b424d",
			"targetRef": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab"
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
				"4c7b7c3f-755c-4227-b757-273305a83d8d": {},
				"e6215272-57b1-47ba-b2e4-75b66d61ef4b": {},
				"7d204c03-1d43-4036-805d-44c152a973df": {},
				"8bda4aa6-e6bb-46b0-9c60-c1ee7530043f": {},
				"366aa3ee-79f9-4782-8f90-42974be2bd12": {},
				"d8614466-5275-4ac0-8377-d2abfbc02bbb": {},
				"cb81d354-03c2-42a5-8786-5b4b97419222": {},
				"8447ab23-c7a8-4980-a520-76425d4877fb": {},
				"63a93f9e-e147-42c4-9eab-27ba35efd724": {},
				"8854ce3b-ba5c-430a-9bda-a59feeccde42": {},
				"0444e215-d4d9-4a6e-8486-7eaf0a0f819d": {},
				"ae2392c6-7433-4d65-a81c-07b723064f72": {},
				"dedfacbb-ba3e-4e26-8281-69628baaecba": {}
			}
		},
		"df898b52-91e1-4778-baad-2ad9a261d30e": {
			"classDefinition": "com.sap.bpm.wfs.ui.StartEventSymbol",
			"x": 12,
			"y": 74.5,
			"width": 32,
			"height": 32,
			"object": "11a9b5ee-17c0-4159-9bbf-454dcfdcd5c3"
		},
		"53e54950-7757-4161-82c9-afa7e86cff2c": {
			"classDefinition": "com.sap.bpm.wfs.ui.EndEventSymbol",
			"x": 1145.9999964237213,
			"y": 73,
			"width": 35,
			"height": 35,
			"object": "2798f4e7-bc42-4fad-a248-159095a2f40a"
		},
		"6bb141da-d485-4317-93b8-e17711df4c32": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "44,90.5 94,90.5",
			"sourceSymbol": "df898b52-91e1-4778-baad-2ad9a261d30e",
			"targetSymbol": "8ce074b8-5b22-4821-9c7a-5d6222f51938",
			"object": "c6b99f32-5fe6-4ab6-b60a-80fba1b9ae0f"
		},
		"8ce074b8-5b22-4821-9c7a-5d6222f51938": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 94,
			"y": 60.5,
			"width": 100,
			"height": 60,
			"object": "ee6e7b60-2913-469d-9236-38403b04cb00"
		},
		"86785bb3-7a4d-4c0c-89a3-36441ce2330b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "194,90.5 244,90.5",
			"sourceSymbol": "8ce074b8-5b22-4821-9c7a-5d6222f51938",
			"targetSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"object": "6ff85cd6-a99c-41e1-bc14-dc1b3dc621e5"
		},
		"99496cae-e05d-4b21-93a6-e69fb4c063fd": {
			"classDefinition": "com.sap.bpm.wfs.ui.ExclusiveGatewaySymbol",
			"x": 244,
			"y": 69.5,
			"object": "5473944c-625e-4932-8f8b-a02bd3bb711d"
		},
		"0ba550bf-68cd-4e49-b35c-12ac9ce15043": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "286,90.5 320.99999940395355,90.5 320.99999940395355,42 355.9999988079071,42",
			"sourceSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"targetSymbol": "7d204c03-1d43-4036-805d-44c152a973df",
			"object": "0ae95448-4949-4ea1-aa4d-442156bea302"
		},
		"4c7b7c3f-755c-4227-b757-273305a83d8d": {
			"classDefinition": "com.sap.bpm.wfs.ui.UserTaskSymbol",
			"x": 675.9999976158142,
			"y": 42.500000298023224,
			"width": 100,
			"height": 60,
			"object": "4740336e-3648-47b0-b40d-d929628b424d"
		},
		"e6215272-57b1-47ba-b2e4-75b66d61ef4b": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "775.9999976158142,72.50000029802322 825.9999976158142,72.50000029802322",
			"sourceSymbol": "4c7b7c3f-755c-4227-b757-273305a83d8d",
			"targetSymbol": "8bda4aa6-e6bb-46b0-9c60-c1ee7530043f",
			"object": "73dc266c-4023-4683-a5bf-f11aaed73345"
		},
		"7d204c03-1d43-4036-805d-44c152a973df": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 355.9999988079071,
			"y": 12,
			"width": 100,
			"height": 60,
			"object": "207903dc-2994-4fc2-97e0-c8b27cf71d73"
		},
		"8bda4aa6-e6bb-46b0-9c60-c1ee7530043f": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 825.9999976158142,
			"y": 42.500000298023224,
			"width": 100,
			"height": 60,
			"object": "1d3d4782-d8c9-4ea6-963f-4a6b9a9f6bab"
		},
		"366aa3ee-79f9-4782-8f90-42974be2bd12": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 995.9999964237213,
			"y": 60.5,
			"width": 100,
			"height": 60,
			"object": "1422dd9d-9fd2-4838-988c-64fbfdd423ab"
		},
		"d8614466-5275-4ac0-8377-d2abfbc02bbb": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "286,90.5 320.99999940395355,90.5 320.99999940395355,145.5 355.9999988079071,145.5",
			"sourceSymbol": "99496cae-e05d-4b21-93a6-e69fb4c063fd",
			"targetSymbol": "63a93f9e-e147-42c4-9eab-27ba35efd724",
			"object": "c8061712-316e-43ab-ad05-920324c507d4"
		},
		"cb81d354-03c2-42a5-8786-5b4b97419222": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "925.9999976158142,72.50000029802322 960.9999970197678,72.50000029802322 960.9999970197678,90.5 995.9999964237213,90.5",
			"sourceSymbol": "8bda4aa6-e6bb-46b0-9c60-c1ee7530043f",
			"targetSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"object": "75b09c3b-2d98-49ed-a479-791eda3afe90"
		},
		"8447ab23-c7a8-4980-a520-76425d4877fb": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "1095.9999964237213,90.5 1145.9999964237213,90.5",
			"sourceSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"targetSymbol": "53e54950-7757-4161-82c9-afa7e86cff2c",
			"object": "870a4a6e-5ba4-47ab-8f79-7a6cd27804c3"
		},
		"63a93f9e-e147-42c4-9eab-27ba35efd724": {
			"classDefinition": "com.sap.bpm.wfs.ui.ScriptTaskSymbol",
			"x": 355.9999988079071,
			"y": 122,
			"width": 100,
			"height": 47,
			"object": "5b1f92fd-79a3-472d-8b4e-98e210516300"
		},
		"62d7f4ed-4063-4c44-af8b-39050bd44926": {
			"classDefinition": "com.sap.bpm.wfs.LastIDs",
			"sequenceflow": 20,
			"startevent": 1,
			"endevent": 2,
			"usertask": 2,
			"servicetask": 4,
			"scripttask": 3,
			"exclusivegateway": 2
		},
		"e96a133c-4c90-4e5f-b70e-b6af7a600080": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow18",
			"name": "SequenceFlow18",
			"sourceRef": "5b1f92fd-79a3-472d-8b4e-98e210516300",
			"targetRef": "1422dd9d-9fd2-4838-988c-64fbfdd423ab"
		},
		"8854ce3b-ba5c-430a-9bda-a59feeccde42": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "455.9999988079071,145.5 490.99999821186066,145.5 490.99999821186066,137.49999970197678 960.9999970197678,137.49999970197678 960.9999970197678,90.5 995.9999964237213,90.5",
			"sourceSymbol": "63a93f9e-e147-42c4-9eab-27ba35efd724",
			"targetSymbol": "366aa3ee-79f9-4782-8f90-42974be2bd12",
			"object": "e96a133c-4c90-4e5f-b70e-b6af7a600080"
		},
		"6e116996-1b13-4619-a770-ecc157994389": {
			"classDefinition": "com.sap.bpm.wfs.ServiceTask",
			"destination": "lch_service_wf",
			"path": "/premium/updateTableDetails",
			"httpMethod": "POST",
			"requestVariable": "${context.workflowRequest}",
			"responseVariable": "${context.workflowResponse}",
			"id": "servicetask4",
			"name": "ServiceTask1"
		},
		"0444e215-d4d9-4a6e-8486-7eaf0a0f819d": {
			"classDefinition": "com.sap.bpm.wfs.ui.ServiceTaskSymbol",
			"x": 525.9999976158142,
			"y": 42.500000298023224,
			"width": 100,
			"height": 60,
			"object": "6e116996-1b13-4619-a770-ecc157994389"
		},
		"43943115-8a35-4467-be9b-cac26564cf21": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow19",
			"name": "SequenceFlow19",
			"sourceRef": "207903dc-2994-4fc2-97e0-c8b27cf71d73",
			"targetRef": "6e116996-1b13-4619-a770-ecc157994389"
		},
		"ae2392c6-7433-4d65-a81c-07b723064f72": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "455.9999988079071,42 490.99999821186066,42 490.99999821186066,72.50000029802322 525.9999976158142,72.50000029802322",
			"sourceSymbol": "7d204c03-1d43-4036-805d-44c152a973df",
			"targetSymbol": "0444e215-d4d9-4a6e-8486-7eaf0a0f819d",
			"object": "43943115-8a35-4467-be9b-cac26564cf21"
		},
		"44fdc2cf-e5c3-4cf2-a2d5-77c5fe3539bb": {
			"classDefinition": "com.sap.bpm.wfs.SequenceFlow",
			"id": "sequenceflow20",
			"name": "SequenceFlow20",
			"sourceRef": "6e116996-1b13-4619-a770-ecc157994389",
			"targetRef": "4740336e-3648-47b0-b40d-d929628b424d"
		},
		"dedfacbb-ba3e-4e26-8281-69628baaecba": {
			"classDefinition": "com.sap.bpm.wfs.ui.SequenceFlowSymbol",
			"points": "625.9999976158142,72.50000029802322 675.9999976158142,72.50000029802322",
			"sourceSymbol": "0444e215-d4d9-4a6e-8486-7eaf0a0f819d",
			"targetSymbol": "4c7b7c3f-755c-4227-b757-273305a83d8d",
			"object": "44fdc2cf-e5c3-4cf2-a2d5-77c5fe3539bb"
		}
	}
}