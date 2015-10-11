package team.dx.classroom.service.impl;

import java.util.List;

import team.dx.classroom.dao.PrivilegeDAO;
import team.dx.classroom.dao.RoleDAO;
import team.dx.classroom.dao.UserDAO;
import team.dx.classroom.domain.Privilege;
import team.dx.classroom.domain.Role;
import team.dx.classroom.domain.User;
import team.dx.classroom.factory.ObjectFactory;
import team.dx.classroom.service.PersonBusinessService;

public class PersonBusinessServiceImpl implements PersonBusinessService {

	UserDAO uDAO = ObjectFactory.getInstance().createObject(UserDAO.class);
	RoleDAO rDAO = ObjectFactory.getInstance().createObject(RoleDAO.class);
	PrivilegeDAO pDAO = ObjectFactory.getInstance().createObject(PrivilegeDAO.class);
	
	@Override
	public User findUserIsExist(String nick) {
		String condition = "select id from user where nick = ?";
		List<User> users = uDAO.getUsers(condition, nick);
		if (users == null || users.size() == 0) {
			return null;
		}
		
		return users.get(0);
	}
	
	@Override
	public User getUser(String email, String password) {
		
		User user = null;
		
		// 先查询user 获取user_id
		String userCondition = "SELECT * FROM user WHERE email = ? AND password = ?";
		
		// 如果为查到则getUsers() 返回空集合
		List<User> users = uDAO.getUsers(userCondition, email, password);
		
		// 为空则返回null
		if (users == null || users.size() == 0) {
			return null;
		}
		
		user = users.get(0);
		
		// 然后根据user_id 获取role 对象
		String roleCondition = "SELECT * FROM role WHERE id = (SELECT role_id FROM user_role WHERE user_id = ?)";
		Role role = rDAO.getRole(roleCondition, user.getId());
		
		if (role == null) {
			return user;
		}
		
		// 再根据role_id 获取privilege 对象
		String privilegeCondition = "SELECT * FROM privilege WHERE id = (SELECT privilege_id FROM role_privilege WHERE role_id = ?)";
		List<Privilege> privileges = pDAO.getPrivileges(privilegeCondition, role.getId());
		
		// 最后组合成完整的User 对象
		role.setPrivileges(privileges);
		user.setRole(role);
		
		return user;
	}

	@Override
	public void addUser(User user) {
		
	}

}
