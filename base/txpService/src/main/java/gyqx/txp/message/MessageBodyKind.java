package gyqx.txp.message;

public enum MessageBodyKind 
{
	TablesSyncCompare,TablesSyncCompareNotify, DataDownload,DataUpload,CallService,
	TablesSyncCompareResult,CallServiceResult,DataDownloadResult,DataUploadResult,
	NotifyMessage,
	FileDownloadRequest,FileUploadRequest,
	FileDownloadResult,FileUploadResult,
	FileCompareRequest,FileCompareResponse,
	RemoteCmdRequest,RemoteCmdResponse
	
}
