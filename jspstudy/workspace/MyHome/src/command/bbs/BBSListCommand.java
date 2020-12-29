package command.bbs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.PageVo;
import common.PathNRedirect;
import dao.BBSDao;

public class BBSListCommand implements BBSCommand {

	@Override
	public PathNRedirect execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 1. PageVo 객체 생성
		PageVo pageVo = new PageVo();
		
		// 2. 전체 레코드의 개수 구하기
		int totalRecord = BBSDao.getInstance().getTotalRecord();
		pageVo.setTotalRecord(totalRecord);
		
		// 3. 전체 페이지의 개수 구하기
		pageVo.setTotalPage();
		
		// 4. 현재 페이지 번호 구하기
		// PageVo 클래스에는 page = 1로 되어 있다.
		String page = request.getParameter("page");
		if ( page != null && !page.isEmpty() ) {
			pageVo.setPage( Integer.parseInt(page) );
		}
		
		// 5. 현재 페이지의 시작 게시물 번호와 종료 게시물 번호 구하기
		// 전체 게시물은 20개이다. (totalRecord)
		// 한 페이지 당 게시물은 3개이다. (recordPerPage)
		// page = 1인 경우 beginRecord = 1, endRecord = 3
		// page = 2인 경우 beginRecord = 4, endRecord = 6
		// page = 3인 경우 beginRecord = 7, endRecord = 9
		// page = 4인 경우 beginRecord = 10, endRecord = 12
		// page = 5인 경우 beginRecord = 13, endRecord = 15
		// page = 6인 경우 beginRecord = 16, endRecord = 18
		// page = 7인 경우 beginRecord = 19, endRecord = 21(실제로는 20임)
		int beginRecord = (pageVo.getPage() - 1) * pageVo.getRecordPerPage() + 1;
		pageVo.setBeginRecord( beginRecord );
		int endRecord = pageVo.getBeginRecord() + pageVo.getRecordPerPage() - 1;
		endRecord = endRecord < totalRecord ? endRecord : totalRecord;
		pageVo.setEndRecord( endRecord );
		
		
		PathNRedirect pathNRedirect = new PathNRedirect();
		pathNRedirect.setPath("bbs/bbsListPage.jsp");
		pathNRedirect.setRedirect(false);
		return pathNRedirect;
		
	}

}
