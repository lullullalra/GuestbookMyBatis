package gntp.lesson.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gntp.lesson.guestbook.util.ConnectionManagerV2;
import gntp.lesson.guestbook.vo.GuestbookVO;
import gntp.lesson.guestbook.vo.ReplyVO;

@Repository("guestbookDAO")
public class GuestbookDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestbookDAO() {}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public boolean insertOne(GuestbookVO book) throws SQLException {
		boolean flag = false;
		int affectedCount = sqlSession.insert("mapper.guestbook.insertGuestbook", book);
		if(affectedCount>0) {
			flag = true;
		}
		
		return flag;
	}
	
	public List<GuestbookVO> selectAll() throws SQLException{
		List<GuestbookVO> list = null;
		list = sqlSession.selectList("mapper.guestbook.selectAllGuestbookList");
		return list;
	}

	public GuestbookVO selectOneForUpdate(int seq) throws SQLException {
		// TODO Auto-generated method stub
		GuestbookVO book = null;
		book = (GuestbookVO)sqlSession.selectOne("mapper.guestbook.selectGuestbookBySeq",seq);
		return book;
	}
	
	public GuestbookVO selectOne(String seq,String token) throws SQLException {
		// TODO Auto-generated method stub
		GuestbookVO book = null;
		ReplyVO reply = null;
		List<ReplyVO> list = null;
		if(token!=null) {
			boolean flag = false;
			int affectedCount = sqlSession.update("mapper.guestbook.updateReadCount", seq);
			if(affectedCount>0) {
				flag = true;
			}
		}
		// 해당글 조회
		
		book = (GuestbookVO) sqlSession.selectOne("mapper.guestbook.selectAllGuestbookListAndReply", seq);
		
		book.setReplyList(list);
		
		return book;
	}

	public boolean updateOne(GuestbookVO book) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		int affectedCount = sqlSession.update("mapper.guestbook.updateGuestbook", book);
		if(affectedCount>0) {
			flag = true;
		}
		return flag;
	}

	public boolean deleteOne(String seq) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		int affectedCount = sqlSession.delete("mapper.guestbook.deleteGuestbook", Integer.parseInt(seq));
		if(affectedCount>0) {
			flag = true;
		}
		return flag;
	}

	public boolean insertReply(ReplyVO reply) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		int affectedCount = sqlSession.insert("mapper.guestbook.insertReply", reply);
		if(affectedCount>0) {
			flag = true;
		}
		return flag;
	}
}







