package dao

type UtilsDAO interface {
	Exec(sql string)
}

func NewUtilsDAO() *UtilsDAOSQLite {
	return &UtilsDAOSQLite{}
}
