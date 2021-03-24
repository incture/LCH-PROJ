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
var response = null;
var createdBy=$.context.createdBy;
var createdDate=$.context.createdDate;
var workflowInfo = {
	orderIdDetails: orderId,
	taskIdDetails: taskId,
	createdBy : createdBy,
    createdDate : createdDate
};


$.context.workflowInfo = workflowInfo;
$.context.adhocOrderId = orderId;
$.context.response = response;





