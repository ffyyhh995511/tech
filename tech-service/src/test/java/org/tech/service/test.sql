			         SELECT  cst.id ,
					        cst.loginid ,
					        cst.lastname ,
					        cst.managerid ,
							 hrmManager.email managerEmail,
					        hrmManager.lastname managerName,
					        cst.startdate ,
					        cst.probationenddate ,
					        cst.subcompanyname ,
					        cst.subcompanyid1 ,
					        cst.departmentid ,
					        cst.CompName ,
					        cst.areaCaption ,
					        hrm.datefield3 contractStartDate ,
					        cst.datefield4 areaCode ,
					        cst.jobtitle ,
					        cst.enddate ,
					        cst.contactType,
							 ISNULL(reNew.status,'未发起流程') status,
							 reNew.lastname operaterName
					 FROM   dbo.HrmResource hrm LEFT JOIN dbo.HrmResource hrmManager ON hrm.managerid=hrmManager.id,dbo.contactSignThreeMonth cst                         	
														LEFT JOIN ( SELECT hr2.lastname lastname,hrn.objLastName lastnameId ,cp.userid,nb.nodename status,rb.requestid FROM dbo.CUX_WF_HrmContractRenew_n hrn ,dbo.workflow_nodebase nb,dbo.workflow_requestbase rb LEFT JOIN (SELECT  MAX(userid) userid,nodeid,requestid from dbo.workflow_currentoperator WHERE isremark=0 GROUP BY nodeid,requestid)cp ON cp.nodeid=rb.currentnodeid AND cp.requestid=rb.requestid LEFT JOIN dbo.HrmResource hr2 ON hr2.id=cp.userid WHERE flowId IS NOT NULL AND  hrn.requestid=rb.requestid AND  hrn.flowId<>''  AND rb.currentnodeid=nb.id AND hrn.applyDate>DATEADD(yy, -1, GETDATE() ) ) reNew  
														ON cst.id=reNew.lastNameId 
					 WHERE   cst.enddate > DATEADD(ms, -3,
					                          DATEADD(mm, DATEDIFF(mm, 0, GETDATE()) + 3, 0))
					        AND cst.enddate < DATEADD(mm, DATEDIFF(mm, 0, GETDATE()) + 4, 0)
					        AND cst.subcompanyid1 = 1044 
							AND cst.id=hrm.id AND hrm.status<>5 