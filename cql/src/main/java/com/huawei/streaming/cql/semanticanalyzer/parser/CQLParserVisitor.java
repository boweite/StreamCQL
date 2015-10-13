// Generated from CQLParser.g4 by ANTLR 4.1

package com.huawei.streaming.cql.semanticanalyzer.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CQLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CQLParser#joinToken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinToken(@NotNull CQLParser.JoinTokenContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#unaryOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(@NotNull CQLParser.UnaryOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sourceDefine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceDefine(@NotNull CQLParser.SourceDefineContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#fullJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullJoin(@NotNull CQLParser.FullJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#multiInsertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiInsertStatement(@NotNull CQLParser.MultiInsertStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeDay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeDay(@NotNull CQLParser.RangeDayContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#innerClassName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerClassName(@NotNull CQLParser.InnerClassNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPath(@NotNull CQLParser.PathContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rightJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightJoin(@NotNull CQLParser.RightJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constDoubleValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDoubleValue(@NotNull CQLParser.ConstDoubleValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#fieldExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldExpression(@NotNull CQLParser.FieldExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfNotExists(@NotNull CQLParser.IfNotExistsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#logicExpressionOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicExpressionOr(@NotNull CQLParser.LogicExpressionOrContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#orderByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderByClause(@NotNull CQLParser.OrderByClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamPropertiesList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamPropertiesList(@NotNull CQLParser.StreamPropertiesListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceQuery}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceQuery(@NotNull CQLParser.DatasourceQueryContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#execStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExecStatement(@NotNull CQLParser.ExecStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#inputSchemaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputSchemaStatement(@NotNull CQLParser.InputSchemaStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#keyValueProperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValueProperty(@NotNull CQLParser.KeyValuePropertyContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#innerJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInnerJoin(@NotNull CQLParser.InnerJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#multialias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultialias(@NotNull CQLParser.MultialiasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#precedenceEqualNegatableOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrecedenceEqualNegatableOperator(@NotNull CQLParser.PrecedenceEqualNegatableOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#distributeClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistributeClause(@NotNull CQLParser.DistributeClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnNameTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnNameTypeList(@NotNull CQLParser.ColumnNameTypeListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#subSelectClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubSelectClause(@NotNull CQLParser.SubSelectClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#expressionWithLaparen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionWithLaparen(@NotNull CQLParser.ExpressionWithLaparenContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#strValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStrValue(@NotNull CQLParser.StrValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#havingCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingCondition(@NotNull CQLParser.HavingConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#outputSchemaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputSchemaStatement(@NotNull CQLParser.OutputSchemaStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#onCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOnCondition(@NotNull CQLParser.OnConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(@NotNull CQLParser.InsertStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sourceClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceClause(@NotNull CQLParser.SourceClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceSchema}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceSchema(@NotNull CQLParser.DatasourceSchemaContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createInputStreamStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateInputStreamStatement(@NotNull CQLParser.CreateInputStreamStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#windowDeterminer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowDeterminer(@NotNull CQLParser.WindowDeterminerContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#ddlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDdlStatement(@NotNull CQLParser.DdlStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#isNullLikeInExpressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsNullLikeInExpressions(@NotNull CQLParser.IsNullLikeInExpressionsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#fromSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromSource(@NotNull CQLParser.FromSourceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#serdeDefine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerdeDefine(@NotNull CQLParser.SerdeDefineContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#multiSelect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiSelect(@NotNull CQLParser.MultiSelectContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#userDefinedClassName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserDefinedClassName(@NotNull CQLParser.UserDefinedClassNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamNameOrAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamNameOrAlias(@NotNull CQLParser.StreamNameOrAliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnALias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnALias(@NotNull CQLParser.ColumnALiasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sourceProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceProperties(@NotNull CQLParser.SourcePropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#nullCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullCondition(@NotNull CQLParser.NullConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#arithmeticStarExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticStarExpression(@NotNull CQLParser.ArithmeticStarExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constNull}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstNull(@NotNull CQLParser.ConstNullContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnNameOrder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnNameOrder(@NotNull CQLParser.ColumnNameOrderContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constLongValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstLongValue(@NotNull CQLParser.ConstLongValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#multiInsert}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiInsert(@NotNull CQLParser.MultiInsertContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#relationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpression(@NotNull CQLParser.RelationExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(@NotNull CQLParser.ClassNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#operatorName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorName(@NotNull CQLParser.OperatorNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#windowBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowBody(@NotNull CQLParser.WindowBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#extended}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtended(@NotNull CQLParser.ExtendedContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#packageNameIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageNameIdentifier(@NotNull CQLParser.PackageNameIdentifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#logicExpressionNot}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicExpressionNot(@NotNull CQLParser.LogicExpressionNotContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#confValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfValue(@NotNull CQLParser.ConfValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constStingValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstStingValue(@NotNull CQLParser.ConstStingValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectClause(@NotNull CQLParser.SelectClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#setStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetStatement(@NotNull CQLParser.SetStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#atomExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpression(@NotNull CQLParser.AtomExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#colType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColType(@NotNull CQLParser.ColTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constFloatValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstFloatValue(@NotNull CQLParser.ConstFloatValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#usingStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsingStatement(@NotNull CQLParser.UsingStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#isForce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsForce(@NotNull CQLParser.IsForceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#serdeProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerdeProperties(@NotNull CQLParser.SerdePropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#subQueryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubQueryExpression(@NotNull CQLParser.SubQueryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#applicationName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationName(@NotNull CQLParser.ApplicationNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#havingClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHavingClause(@NotNull CQLParser.HavingClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull CQLParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#bitOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitOperator(@NotNull CQLParser.BitOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#fromClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromClause(@NotNull CQLParser.FromClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#windowSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowSource(@NotNull CQLParser.WindowSourceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamProperties(@NotNull CQLParser.StreamPropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#searchCondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearchCondition(@NotNull CQLParser.SearchConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectItem(@NotNull CQLParser.SelectItemContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createOperatorStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateOperatorStatement(@NotNull CQLParser.CreateOperatorStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#joinRigthBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinRigthBody(@NotNull CQLParser.JoinRigthBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#insertUserOperatorStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertUserOperatorStatement(@NotNull CQLParser.InsertUserOperatorStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#dataSourceName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataSourceName(@NotNull CQLParser.DataSourceNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#logicExpressionAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicExpressionAnd(@NotNull CQLParser.LogicExpressionAndContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceProperties(@NotNull CQLParser.DatasourcePropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeBound(@NotNull CQLParser.RangeBoundContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#limitRow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitRow(@NotNull CQLParser.LimitRowContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#ifExists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExists(@NotNull CQLParser.IfExistsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#serdeClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSerdeClass(@NotNull CQLParser.SerdeClassContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createPipeStreamStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatePipeStreamStatement(@NotNull CQLParser.CreatePipeStreamStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#showFunctions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowFunctions(@NotNull CQLParser.ShowFunctionsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#equalRelationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualRelationExpression(@NotNull CQLParser.EqualRelationExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#windowProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowProperties(@NotNull CQLParser.WindowPropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeWindow(@NotNull CQLParser.RangeWindowContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(@NotNull CQLParser.ColumnNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#groupByList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByList(@NotNull CQLParser.GroupByListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeMinutes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeMinutes(@NotNull CQLParser.RangeMinutesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(@NotNull CQLParser.WhereClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#dropFunctionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropFunctionStatement(@NotNull CQLParser.DropFunctionStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(@NotNull CQLParser.ConstantContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamBody(@NotNull CQLParser.StreamBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createDataSourceStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateDataSourceStatement(@NotNull CQLParser.CreateDataSourceStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#partitionbyDeterminer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPartitionbyDeterminer(@NotNull CQLParser.PartitionbyDeterminerContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#relationOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationOperator(@NotNull CQLParser.RelationOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#limitAll}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitAll(@NotNull CQLParser.LimitAllContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#confName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfName(@NotNull CQLParser.ConfNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(@NotNull CQLParser.CastExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnNameType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnNameType(@NotNull CQLParser.ColumnNameTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(@NotNull CQLParser.FunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#groupByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByClause(@NotNull CQLParser.GroupByClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeTime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeTime(@NotNull CQLParser.RangeTimeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectAlias(@NotNull CQLParser.SelectAliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constIntegerValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstIntegerValue(@NotNull CQLParser.ConstIntegerValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#windowName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWindowName(@NotNull CQLParser.WindowNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(@NotNull CQLParser.SelectStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sinkClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSinkClause(@NotNull CQLParser.SinkClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#getStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetStatement(@NotNull CQLParser.GetStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#identifierNot}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierNot(@NotNull CQLParser.IdentifierNotContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceBody(@NotNull CQLParser.DatasourceBodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#showApplications}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShowApplications(@NotNull CQLParser.ShowApplicationsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#insertClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertClause(@NotNull CQLParser.InsertClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#submitApplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubmitApplication(@NotNull CQLParser.SubmitApplicationContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(@NotNull CQLParser.ExpressionsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#binaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpression(@NotNull CQLParser.BinaryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#explainStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplainStatement(@NotNull CQLParser.ExplainStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createFunctionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateFunctionStatement(@NotNull CQLParser.CreateFunctionStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#joinSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinSource(@NotNull CQLParser.JoinSourceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceQueryArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceQueryArguments(@NotNull CQLParser.DatasourceQueryArgumentsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#leftJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftJoin(@NotNull CQLParser.LeftJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#booleanValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanValue(@NotNull CQLParser.BooleanValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#unidirection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnidirection(@NotNull CQLParser.UnidirectionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#groupByExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupByExpression(@NotNull CQLParser.GroupByExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sourceAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSourceAlias(@NotNull CQLParser.SourceAliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rowsWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRowsWindow(@NotNull CQLParser.RowsWindowContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnOrder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnOrder(@NotNull CQLParser.ColumnOrderContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#datasourceArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasourceArguments(@NotNull CQLParser.DatasourceArgumentsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeUnBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeUnBound(@NotNull CQLParser.RangeUnBoundContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#distinct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDistinct(@NotNull CQLParser.DistinctContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamSource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamSource(@NotNull CQLParser.StreamSourceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#arithmeticPlusOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticPlusOperator(@NotNull CQLParser.ArithmeticPlusOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sinkProperties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSinkProperties(@NotNull CQLParser.SinkPropertiesContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#crossJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCrossJoin(@NotNull CQLParser.CrossJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectExpression(@NotNull CQLParser.SelectExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#naturalJoin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaturalJoin(@NotNull CQLParser.NaturalJoinContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeSeconds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeSeconds(@NotNull CQLParser.RangeSecondsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#columnNameOrderList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnNameOrderList(@NotNull CQLParser.ColumnNameOrderListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull CQLParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#addJARStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddJARStatement(@NotNull CQLParser.AddJARStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#constBigDecimalValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstBigDecimalValue(@NotNull CQLParser.ConstBigDecimalValueContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#subQuerySource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubQuerySource(@NotNull CQLParser.SubQuerySourceContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(@NotNull CQLParser.PrimitiveTypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#addFileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddFileStatement(@NotNull CQLParser.AddFileStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#parallelClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParallelClause(@NotNull CQLParser.ParallelClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#createOutputStreamStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateOutputStreamStatement(@NotNull CQLParser.CreateOutputStreamStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(@NotNull CQLParser.FunctionNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#loadStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoadStatement(@NotNull CQLParser.LoadStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamAlias(@NotNull CQLParser.StreamAliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#limitClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLimitClause(@NotNull CQLParser.LimitClauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeHour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeHour(@NotNull CQLParser.RangeHourContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#cqlIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCqlIdentifier(@NotNull CQLParser.CqlIdentifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamName(@NotNull CQLParser.StreamNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#dropApplication}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDropApplication(@NotNull CQLParser.DropApplicationContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#arithmeticPlusMinusExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticPlusMinusExpression(@NotNull CQLParser.ArithmeticPlusMinusExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#selectList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectList(@NotNull CQLParser.SelectListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#streamAllColumns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStreamAllColumns(@NotNull CQLParser.StreamAllColumnsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#commentString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommentString(@NotNull CQLParser.CommentStringContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#singleAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAlias(@NotNull CQLParser.SingleAliasContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#bitExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBitExpression(@NotNull CQLParser.BitExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#rangeMilliSeconds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRangeMilliSeconds(@NotNull CQLParser.RangeMilliSecondsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#sinkDefine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSinkDefine(@NotNull CQLParser.SinkDefineContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(@NotNull CQLParser.CommentContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#filterBeforeWindow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterBeforeWindow(@NotNull CQLParser.FilterBeforeWindowContext ctx);

	/**
	 * Visit a parse tree produced by {@link CQLParser#arithmeticStarOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticStarOperator(@NotNull CQLParser.ArithmeticStarOperatorContext ctx);
}