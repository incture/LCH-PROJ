/*
// read from existing workflow context 
var productInfo = $.context.productInfo; 
var productName = productInfo.productName; 
var productDescription = productInfo.productDescription;

// read contextual information
var taskDefinitionId = $.info.taskDefinitionId;

// read user task information
var lastUserTask1 = $.usertasks.usertask1.last;
var userTaskSubject = lastUserTask1.subject;
var userTaskProcessor = lastUserTask1.processor;
var userTaskCompletedAt = lastUserTask1.completedAt;

var userTaskStatusMessage = " User task '" + userTaskSubject + "' has been completed by " + userTaskProcessor + " at " + userTaskCompletedAt;

// create new node 'product'
var product = {
		productDetails: productName  + " " + productDescription,
		workflowStep: taskDefinitionId
};

// write 'product' node to workflow context
$.context.product = product;
*/

var orderId = $.context.adhocOrderId;
var taskId = $.context.taskId;
var tokenInfo = $.context.responseMessage;
var token = tokenInfo.access_token;
var response = null;
var workflowInfo = {
	orderIdDetails: orderId,
	taskIdDetails: taskId
};
var workflowTokenInfo = {
	orderIdDetails: orderId,
	taskIdDetails: taskId,
	tokenDetails: token
};

$.context.workflowInfo = workflowInfo;
$.context.workflowTokenInfo = workflowTokenInfo;
$.context.taskId = taskId;
$.context.adhocOrderId = orderId;
$.context.workflowInfo.response = response;
$.context.token = token;





