package gntp.lesson.guestbook.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import gntp.lesson.guestbook.dao.GuestbookDAO;
import gntp.lesson.guestbook.vo.GuestbookVO;
import gntp.lesson.guestbook.vo.ReplyVO;

@Controller("bookController")
@RequestMapping("/guestbook")
public class BookController {
	
	@Autowired
	private GuestbookDAO guestbookDAO;
	
	@RequestMapping(value="/listBook.do", method=RequestMethod.GET)
	public ModelAndView listBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<GuestbookVO> list = guestbookDAO.selectAll();
		mav.addObject("list", list);
		String viewName = this.getViewName(request);
		mav.setViewName("listBook");
		return mav;
	}
	
	@RequestMapping(value="/deleteBook.do", method=RequestMethod.GET)
	public ModelAndView deleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String seq = request.getParameter("seq");
		guestbookDAO.deleteOne(seq);
		
		List<GuestbookVO> list = guestbookDAO.selectAll();
		mav.addObject("list", list);
		
		mav.setViewName("listBook");
		return mav;
	}
	
	@RequestMapping(value="/writeBook.do", method=RequestMethod.GET)
	public ModelAndView writeBook(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("writeBook");
		return mav;
	}
	
	@RequestMapping(value="/readBook.do", method=RequestMethod.GET)
	public ModelAndView readBook(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ModelAndView mav = new ModelAndView();
		GuestbookVO book = null;
		String seq = request.getParameter("seq");
		String token = request.getParameter("token");
		System.out.println(seq);
		System.out.println(token);
		book = guestbookDAO.selectOne(seq, token);
		mav.addObject("book", book);
		
		mav.setViewName("readBook");
		return mav;
	}
	
	@RequestMapping(value="/insertBook.do", method=RequestMethod.POST)
	public ModelAndView insertBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userId = request.getParameter("userId");
		
		GuestbookVO book = new GuestbookVO();
		book.setTitle(title);
		book.setContent(content);
		book.setUserId(userId);
		
		guestbookDAO.insertOne(book);

		List<GuestbookVO> list = guestbookDAO.selectAll();
		mav.addObject("list", list);
		
		mav.setViewName("listBook");
		
		return mav;
	}
	
	@RequestMapping(value="/viewUpdateBook.do", method=RequestMethod.POST)
	public ModelAndView viewUpdateBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		int seq = Integer.parseInt(request.getParameter("seq"));
		GuestbookVO book = guestbookDAO.selectOneForUpdate(seq);
		mav.addObject("book", book);

		mav.setViewName("viewUpdateBook");
		return mav;
	}
	
	@RequestMapping(value="/updateBook.do", method=RequestMethod.POST)
	public ModelAndView updateBook(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		int seq = Integer.parseInt(request.getParameter("seq"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int readCount = Integer.parseInt(request.getParameter("readCount"));
		GuestbookVO book = new GuestbookVO();
		book.setTitle(title);
		book.setContent(content);
		book.setReadCount(readCount);
		book.setSeq(seq);
		guestbookDAO.updateOne(book);
		
		List<GuestbookVO> list = guestbookDAO.selectAll();
		mav.addObject("list", list);
		
		mav.setViewName("listBook");
		return mav;
	}
	
	@RequestMapping(value="/writeReply.do", method=RequestMethod.POST)
	public ModelAndView writeReply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String reply = request.getParameter("reply");
		String seq = request.getParameter("seq");
		ReplyVO vo = new ReplyVO();
		vo.setSeq(Integer.parseInt(seq));
		vo.setContent(reply);
		System.out.println(seq);
		System.out.println(reply);
		guestbookDAO.insertReply(vo);
		
		mav.setViewName("readBook");
		return mav;
	}

	
	private  String getViewName(HttpServletRequest request) throws Exception {
	      String contextPath = request.getContextPath();
	      String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
	      if(uri == null || uri.trim().equals("")) {
	         uri = request.getRequestURI();
	      }
	      
	      //http://localhost:8090/member/listMember.do로 요청시
	      int begin = 0;  //
	      if(!((contextPath==null)||("".equals(contextPath)))){
	         begin = contextPath.length();  // 전체 요청명 의 길이를 구함
	      }

	      int end;
	      if(uri.indexOf(";")!=-1){
	         end=uri.indexOf(";");  //요청 uri에 ';'가 있을 경우 ';'문자 위치를 구함
	      }else if(uri.indexOf("?")!=-1){
	         end=uri.indexOf("?");   //요청 uri에 '?'가 있을 경우 '?' 문자 위치를 구함
	      }else{
	         end=uri.length();
	      }

	      //http://localhost:8090/member/listMember.do로 요청시 먼저 '.do'를 제거한 http://localhost:8090/member/listMember를 구한 후,
	      //다시 http://localhost:8090/member/listMember에서 역순으로 첫번째 '/' 위치를 구한 후, 그 뒤의 listMember를 구한다.
	      String fileName=uri.substring(begin,end);
	      if(fileName.indexOf(".")!=-1){
	         fileName=fileName.substring(0,fileName.lastIndexOf("."));  //요청명에서 역순으로 최초 '.'의 위치를 구한후, '.do' 앞에까지의 문자열을 구함
	      }
	      if(fileName.lastIndexOf("/")!=-1){
	         fileName=fileName.substring(fileName.lastIndexOf("/"),fileName.length()); //요청명에서 역순으로 최초 '/'의 위치를 구한후, '/' 다음부터의 문자열을 구함  
	      }
	      return fileName;
	   }
}
