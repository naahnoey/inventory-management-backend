(function()
{
    return function()
    {
        if (!this._is_form)
            return;
        
        var obj = null;
        
        this.on_create = function()
        {
            this.set_name("Dashboard");
            this.set_titletext("New Form");
            if (Form == this.constructor)
            {
                this._setFormPosition(1280,720);
            }
            
            // Object(Dataset, ExcelExportObject) Initialize
            obj = new Dataset("dsSummary", this);
            obj._setContents("<ColumnInfo><Column id=\"totalItemCount\" type=\"INT\" size=\"256\"/><Column id=\"totalStockQty\" type=\"INT\" size=\"256\"/><Column id=\"lowStockCount\" type=\"INT\" size=\"256\"/><Column id=\"categoryCount\" type=\"INT\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);


            obj = new Dataset("dsCategoryStock", this);
            obj._setContents("<ColumnInfo><Column id=\"categoryName\" type=\"STRING\" size=\"100\"/><Column id=\"itemCount\" type=\"INT\" size=\"256\"/><Column id=\"totalQty\" type=\"INT\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);


            obj = new Dataset("dsLowStock", this);
            obj._setContents("<ColumnInfo><Column id=\"itemName\" type=\"STRING\" size=\"200\"/><Column id=\"itemCode\" type=\"STRING\" size=\"50\"/><Column id=\"minQty\" type=\"INT\" size=\"256\"/><Column id=\"currentQty\" type=\"INT\" size=\"256\"/></ColumnInfo>");
            this.addChild(obj.name, obj);


            obj = new Dataset("dsHistory", this);
            obj._setContents("<ColumnInfo><Column id=\"itemName\" type=\"STRING\" size=\"200\"/><Column id=\"historyType\" type=\"STRING\" size=\"3\"/><Column id=\"qty\" type=\"INT\" size=\"256\"/><Column id=\"reason\" type=\"STRING\" size=\"500\"/><Column id=\"createdBy\" type=\"STRING\" size=\"100\"/><Column id=\"createdAt\" type=\"STRING\" size=\"20\"/></ColumnInfo>");
            this.addChild(obj.name, obj);
            
            // UI Components Initialize
            obj = new Static("stLabelTotalItem","46","158","123","41",null,null,null,null,null,null,this);
            obj.set_taborder("0");
            obj.set_text("전체 물품 수");
            this.addChild(obj.name, obj);

            obj = new Static("stTotalItem","190","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("1");
            obj.set_text("0개");
            this.addChild(obj.name, obj);

            obj = new Static("stLabelTotalStock","320","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("2");
            obj.set_text("총 재고 수량");
            this.addChild(obj.name, obj);

            obj = new Static("stTotalStock","450","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("3");
            obj.set_text("0개");
            this.addChild(obj.name, obj);

            obj = new Static("stLabelLowStock","570","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("4");
            obj.set_text("재고 부족");
            this.addChild(obj.name, obj);

            obj = new Static("stLowStock","700","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("5");
            obj.set_text("0개");
            this.addChild(obj.name, obj);

            obj = new Static("stLabelCategory","890","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("6");
            obj.set_text("카테고리 수");
            this.addChild(obj.name, obj);

            obj = new Static("stCategory","1090","150","120","60",null,null,null,null,null,null,this);
            obj.set_taborder("7");
            obj.set_text("0개");
            this.addChild(obj.name, obj);

            obj = new Grid("grdCategory","60","240","250","430",null,null,null,null,null,null,this);
            obj.set_taborder("8");
            obj.set_binddataset("dsCategoryStock");
            obj._setContents("<Formats><Format id=\"default\"><Columns><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/></Columns><Rows><Row size=\"24\" band=\"head\"/><Row size=\"24\"/></Rows><Band id=\"head\"><Cell text=\"카테고리\"/><Cell col=\"1\" text=\"물품 수\"/><Cell col=\"2\" text=\"총 재고\"/></Band><Band id=\"body\"><Cell text=\"bind:categoryName\"/><Cell col=\"1\" text=\"bind:itemCount\"/><Cell col=\"2\" text=\"bind:totalQty\"/></Band></Format></Formats>");
            this.addChild(obj.name, obj);

            obj = new Grid("grdLowStock","360","240","860","192",null,null,null,null,null,null,this);
            obj.set_taborder("9");
            obj.set_binddataset("dsLowStock");
            obj._setContents("<Formats><Format id=\"default\"><Columns><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/></Columns><Rows><Row size=\"24\" band=\"head\"/><Row size=\"24\"/></Rows><Band id=\"head\"><Cell text=\"물품명\"/><Cell col=\"1\" text=\"물품코드\"/><Cell col=\"2\" text=\"최소수량\"/><Cell col=\"3\" text=\"현재수량\"/></Band><Band id=\"body\"><Cell text=\"bind:itemName\"/><Cell col=\"1\" text=\"bind:itemCode\"/><Cell col=\"2\" text=\"bind:minQty\"/><Cell col=\"3\" text=\"bind:currentQty\"/></Band></Format></Formats>");
            this.addChild(obj.name, obj);

            obj = new Grid("grdHistory","360","460","870","215",null,null,null,null,null,null,this);
            obj.set_taborder("10");
            obj.set_binddataset("dsHistory");
            obj._setContents("<Formats><Format id=\"default\"><Columns><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/><Column size=\"80\"/></Columns><Rows><Row size=\"24\" band=\"head\"/><Row size=\"24\"/></Rows><Band id=\"head\"><Cell text=\"물품명\"/><Cell col=\"1\" text=\"구분\"/><Cell col=\"2\" text=\"수량\"/><Cell col=\"3\" text=\"사유\"/><Cell col=\"4\" text=\"처리자\"/><Cell col=\"5\" text=\"처리일시\"/></Band><Band id=\"body\"><Cell text=\"bind:itemName\"/><Cell col=\"1\" text=\"bind:historyType\"/><Cell col=\"2\" text=\"bind:qty\"/><Cell col=\"3\" text=\"bind:reason\"/><Cell col=\"4\" text=\"bind:createdBy\"/><Cell col=\"5\" text=\"bind:createdAt\"/></Band></Format></Formats>");
            this.addChild(obj.name, obj);

            obj = new Button("btnRefresh","1075","63","111","70",null,null,null,null,null,null,this);
            obj.set_taborder("11");
            obj.set_text("새로고침");
            this.addChild(obj.name, obj);
            // Layout Functions
            //-- Default Layout : this
            obj = new Layout("default","",1280,720,this,function(p){});
            obj.set_mobileorientation("landscape");
            this.addLayout(obj.name, obj);
            
            // BindItem Information

            
            // TriggerItem Information

        };
        
        this.loadPreloadList = function()
        {

        };
        
        // User Script
        this.registerScript("Dashboard.xfdl", function() {
        // 화면 로드 시 자동 실행
        this.Dashboard_onload = function(obj, e) {
            this.fn_getDashboard();
        }

        // 새로고침 버튼 클릭
        this.btnRefresh_onclick = function(obj, e) {
            this.fn_getDashboard();
        }

        // 대시보드 조회
        this.fn_getDashboard = function() {
            this.transaction(
                "getDashboard",                          // 트랜잭션 ID
                "SVC::nexacro/getDashboard.do",          // 서비스 URL
                "",                                      // 전송 Dataset (없음)
                "dsSummary=dsSummary "
                + "dsCategoryStock=dsCategoryStock "
                + "dsLowStock=dsLowStock "
                + "dsHistory=dsHistory",                 // 수신 Dataset
                "",                                      // 파라미터
                "fn_getDashboardCallBack"                // 콜백 함수
            );
        }

        // 조회 완료 콜백
        this.fn_getDashboardCallBack = function(svcId, errorCode, errorMsg) {
            if (errorCode < 0) {
                alert("오류 발생: " + errorMsg);
                return;
            }

            // 요약 카드 수치 업데이트
            this.stTotalItem.set_text(this.dsSummary.getColumn(0, "totalItemCount") + " 개");
            this.stTotalStock.set_text(this.dsSummary.getColumn(0, "totalStockQty") + " 개");
            this.stLowStock.set_text(this.dsSummary.getColumn(0, "lowStockCount") + " 개");
            this.stCategory.set_text(this.dsSummary.getColumn(0, "categoryCount") + " 개");

            // Grid는 Dataset에 바인딩되어 있으면 자동으로 표시
        }
        });
        
        // Regist UI Components Event
        this.on_initEvent = function()
        {
            this.stLabelTotalItem.addEventHandler("onclick",this.Static00_onclick,this);
        };
        this.loadIncludeScript("Dashboard.xfdl");
        this.loadPreloadList();
        
        // Remove Reference
        obj = null;
    };
}
)();
