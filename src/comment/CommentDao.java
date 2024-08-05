package comment;


import member.Member;

public interface CommentDao {

    int addComment(Comment comment);

    int updateComment(Comment comment, Member member);

    int deleteComment(Comment comment, Member member);

    Comment[] getAllComments(int boardId);
}
