// Generated from CQLParser.g4 by ANTLR 4.1

package com.huawei.streaming.cql.semanticanalyzer.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CQLParser}.
 */
public interface CQLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CQLParser#joinToken}.
	 * @param ctx the parse tree
	 */
	void enterJoinToken(@NotNull CQLParser.JoinTokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#joinToken}.
	 * @param ctx the parse tree
	 */
	void exitJoinToken(@NotNull CQLParser.JoinTokenContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(@NotNull CQLParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(@NotNull CQLParser.UnaryOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sourceDefine}.
	 * @param ctx the parse tree
	 */
	void enterSourceDefine(@NotNull CQLParser.SourceDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sourceDefine}.
	 * @param ctx the parse tree
	 */
	void exitSourceDefine(@NotNull CQLParser.SourceDefineContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#fullJoin}.
	 * @param ctx the parse tree
	 */
	void enterFullJoin(@NotNull CQLParser.FullJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#fullJoin}.
	 * @param ctx the parse tree
	 */
	void exitFullJoin(@NotNull CQLParser.FullJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#multiInsertStatement}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsertStatement(@NotNull CQLParser.MultiInsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#multiInsertStatement}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsertStatement(@NotNull CQLParser.MultiInsertStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeDay}.
	 * @param ctx the parse tree
	 */
	void enterRangeDay(@NotNull CQLParser.RangeDayContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeDay}.
	 * @param ctx the parse tree
	 */
	void exitRangeDay(@NotNull CQLParser.RangeDayContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#innerClassName}.
	 * @param ctx the parse tree
	 */
	void enterInnerClassName(@NotNull CQLParser.InnerClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#innerClassName}.
	 * @param ctx the parse tree
	 */
	void exitInnerClassName(@NotNull CQLParser.InnerClassNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(@NotNull CQLParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(@NotNull CQLParser.PathContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rightJoin}.
	 * @param ctx the parse tree
	 */
	void enterRightJoin(@NotNull CQLParser.RightJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rightJoin}.
	 * @param ctx the parse tree
	 */
	void exitRightJoin(@NotNull CQLParser.RightJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constDoubleValue}.
	 * @param ctx the parse tree
	 */
	void enterConstDoubleValue(@NotNull CQLParser.ConstDoubleValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constDoubleValue}.
	 * @param ctx the parse tree
	 */
	void exitConstDoubleValue(@NotNull CQLParser.ConstDoubleValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#fieldExpression}.
	 * @param ctx the parse tree
	 */
	void enterFieldExpression(@NotNull CQLParser.FieldExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#fieldExpression}.
	 * @param ctx the parse tree
	 */
	void exitFieldExpression(@NotNull CQLParser.FieldExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void enterIfNotExists(@NotNull CQLParser.IfNotExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void exitIfNotExists(@NotNull CQLParser.IfNotExistsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#logicExpressionOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicExpressionOr(@NotNull CQLParser.LogicExpressionOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#logicExpressionOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicExpressionOr(@NotNull CQLParser.LogicExpressionOrContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#orderByClause}.
	 * @param ctx the parse tree
	 */
	void enterOrderByClause(@NotNull CQLParser.OrderByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#orderByClause}.
	 * @param ctx the parse tree
	 */
	void exitOrderByClause(@NotNull CQLParser.OrderByClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamPropertiesList}.
	 * @param ctx the parse tree
	 */
	void enterStreamPropertiesList(@NotNull CQLParser.StreamPropertiesListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamPropertiesList}.
	 * @param ctx the parse tree
	 */
	void exitStreamPropertiesList(@NotNull CQLParser.StreamPropertiesListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceQuery}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceQuery(@NotNull CQLParser.DatasourceQueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceQuery}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceQuery(@NotNull CQLParser.DatasourceQueryContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#execStatement}.
	 * @param ctx the parse tree
	 */
	void enterExecStatement(@NotNull CQLParser.ExecStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#execStatement}.
	 * @param ctx the parse tree
	 */
	void exitExecStatement(@NotNull CQLParser.ExecStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#inputSchemaStatement}.
	 * @param ctx the parse tree
	 */
	void enterInputSchemaStatement(@NotNull CQLParser.InputSchemaStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#inputSchemaStatement}.
	 * @param ctx the parse tree
	 */
	void exitInputSchemaStatement(@NotNull CQLParser.InputSchemaStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#keyValueProperty}.
	 * @param ctx the parse tree
	 */
	void enterKeyValueProperty(@NotNull CQLParser.KeyValuePropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#keyValueProperty}.
	 * @param ctx the parse tree
	 */
	void exitKeyValueProperty(@NotNull CQLParser.KeyValuePropertyContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#innerJoin}.
	 * @param ctx the parse tree
	 */
	void enterInnerJoin(@NotNull CQLParser.InnerJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#innerJoin}.
	 * @param ctx the parse tree
	 */
	void exitInnerJoin(@NotNull CQLParser.InnerJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#multialias}.
	 * @param ctx the parse tree
	 */
	void enterMultialias(@NotNull CQLParser.MultialiasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#multialias}.
	 * @param ctx the parse tree
	 */
	void exitMultialias(@NotNull CQLParser.MultialiasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#precedenceEqualNegatableOperator}.
	 * @param ctx the parse tree
	 */
	void enterPrecedenceEqualNegatableOperator(@NotNull CQLParser.PrecedenceEqualNegatableOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#precedenceEqualNegatableOperator}.
	 * @param ctx the parse tree
	 */
	void exitPrecedenceEqualNegatableOperator(@NotNull CQLParser.PrecedenceEqualNegatableOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#distributeClause}.
	 * @param ctx the parse tree
	 */
	void enterDistributeClause(@NotNull CQLParser.DistributeClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#distributeClause}.
	 * @param ctx the parse tree
	 */
	void exitDistributeClause(@NotNull CQLParser.DistributeClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnNameTypeList}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameTypeList(@NotNull CQLParser.ColumnNameTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnNameTypeList}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameTypeList(@NotNull CQLParser.ColumnNameTypeListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#subSelectClause}.
	 * @param ctx the parse tree
	 */
	void enterSubSelectClause(@NotNull CQLParser.SubSelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#subSelectClause}.
	 * @param ctx the parse tree
	 */
	void exitSubSelectClause(@NotNull CQLParser.SubSelectClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#expressionWithLaparen}.
	 * @param ctx the parse tree
	 */
	void enterExpressionWithLaparen(@NotNull CQLParser.ExpressionWithLaparenContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#expressionWithLaparen}.
	 * @param ctx the parse tree
	 */
	void exitExpressionWithLaparen(@NotNull CQLParser.ExpressionWithLaparenContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#strValue}.
	 * @param ctx the parse tree
	 */
	void enterStrValue(@NotNull CQLParser.StrValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#strValue}.
	 * @param ctx the parse tree
	 */
	void exitStrValue(@NotNull CQLParser.StrValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void enterHavingCondition(@NotNull CQLParser.HavingConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#havingCondition}.
	 * @param ctx the parse tree
	 */
	void exitHavingCondition(@NotNull CQLParser.HavingConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#outputSchemaStatement}.
	 * @param ctx the parse tree
	 */
	void enterOutputSchemaStatement(@NotNull CQLParser.OutputSchemaStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#outputSchemaStatement}.
	 * @param ctx the parse tree
	 */
	void exitOutputSchemaStatement(@NotNull CQLParser.OutputSchemaStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#onCondition}.
	 * @param ctx the parse tree
	 */
	void enterOnCondition(@NotNull CQLParser.OnConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#onCondition}.
	 * @param ctx the parse tree
	 */
	void exitOnCondition(@NotNull CQLParser.OnConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void enterInsertStatement(@NotNull CQLParser.InsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void exitInsertStatement(@NotNull CQLParser.InsertStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sourceClause}.
	 * @param ctx the parse tree
	 */
	void enterSourceClause(@NotNull CQLParser.SourceClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sourceClause}.
	 * @param ctx the parse tree
	 */
	void exitSourceClause(@NotNull CQLParser.SourceClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceSchema}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceSchema(@NotNull CQLParser.DatasourceSchemaContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceSchema}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceSchema(@NotNull CQLParser.DatasourceSchemaContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createInputStreamStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateInputStreamStatement(@NotNull CQLParser.CreateInputStreamStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createInputStreamStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateInputStreamStatement(@NotNull CQLParser.CreateInputStreamStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#windowDeterminer}.
	 * @param ctx the parse tree
	 */
	void enterWindowDeterminer(@NotNull CQLParser.WindowDeterminerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#windowDeterminer}.
	 * @param ctx the parse tree
	 */
	void exitWindowDeterminer(@NotNull CQLParser.WindowDeterminerContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#ddlStatement}.
	 * @param ctx the parse tree
	 */
	void enterDdlStatement(@NotNull CQLParser.DdlStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#ddlStatement}.
	 * @param ctx the parse tree
	 */
	void exitDdlStatement(@NotNull CQLParser.DdlStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#isNullLikeInExpressions}.
	 * @param ctx the parse tree
	 */
	void enterIsNullLikeInExpressions(@NotNull CQLParser.IsNullLikeInExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#isNullLikeInExpressions}.
	 * @param ctx the parse tree
	 */
	void exitIsNullLikeInExpressions(@NotNull CQLParser.IsNullLikeInExpressionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#fromSource}.
	 * @param ctx the parse tree
	 */
	void enterFromSource(@NotNull CQLParser.FromSourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#fromSource}.
	 * @param ctx the parse tree
	 */
	void exitFromSource(@NotNull CQLParser.FromSourceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#serdeDefine}.
	 * @param ctx the parse tree
	 */
	void enterSerdeDefine(@NotNull CQLParser.SerdeDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#serdeDefine}.
	 * @param ctx the parse tree
	 */
	void exitSerdeDefine(@NotNull CQLParser.SerdeDefineContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#multiSelect}.
	 * @param ctx the parse tree
	 */
	void enterMultiSelect(@NotNull CQLParser.MultiSelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#multiSelect}.
	 * @param ctx the parse tree
	 */
	void exitMultiSelect(@NotNull CQLParser.MultiSelectContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#userDefinedClassName}.
	 * @param ctx the parse tree
	 */
	void enterUserDefinedClassName(@NotNull CQLParser.UserDefinedClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#userDefinedClassName}.
	 * @param ctx the parse tree
	 */
	void exitUserDefinedClassName(@NotNull CQLParser.UserDefinedClassNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamNameOrAlias}.
	 * @param ctx the parse tree
	 */
	void enterStreamNameOrAlias(@NotNull CQLParser.StreamNameOrAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamNameOrAlias}.
	 * @param ctx the parse tree
	 */
	void exitStreamNameOrAlias(@NotNull CQLParser.StreamNameOrAliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnALias}.
	 * @param ctx the parse tree
	 */
	void enterColumnALias(@NotNull CQLParser.ColumnALiasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnALias}.
	 * @param ctx the parse tree
	 */
	void exitColumnALias(@NotNull CQLParser.ColumnALiasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sourceProperties}.
	 * @param ctx the parse tree
	 */
	void enterSourceProperties(@NotNull CQLParser.SourcePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sourceProperties}.
	 * @param ctx the parse tree
	 */
	void exitSourceProperties(@NotNull CQLParser.SourcePropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#nullCondition}.
	 * @param ctx the parse tree
	 */
	void enterNullCondition(@NotNull CQLParser.NullConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#nullCondition}.
	 * @param ctx the parse tree
	 */
	void exitNullCondition(@NotNull CQLParser.NullConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#arithmeticStarExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticStarExpression(@NotNull CQLParser.ArithmeticStarExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#arithmeticStarExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticStarExpression(@NotNull CQLParser.ArithmeticStarExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constNull}.
	 * @param ctx the parse tree
	 */
	void enterConstNull(@NotNull CQLParser.ConstNullContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constNull}.
	 * @param ctx the parse tree
	 */
	void exitConstNull(@NotNull CQLParser.ConstNullContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnNameOrder}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameOrder(@NotNull CQLParser.ColumnNameOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnNameOrder}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameOrder(@NotNull CQLParser.ColumnNameOrderContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constLongValue}.
	 * @param ctx the parse tree
	 */
	void enterConstLongValue(@NotNull CQLParser.ConstLongValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constLongValue}.
	 * @param ctx the parse tree
	 */
	void exitConstLongValue(@NotNull CQLParser.ConstLongValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#multiInsert}.
	 * @param ctx the parse tree
	 */
	void enterMultiInsert(@NotNull CQLParser.MultiInsertContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#multiInsert}.
	 * @param ctx the parse tree
	 */
	void exitMultiInsert(@NotNull CQLParser.MultiInsertContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationExpression(@NotNull CQLParser.RelationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#relationExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationExpression(@NotNull CQLParser.RelationExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(@NotNull CQLParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(@NotNull CQLParser.ClassNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#operatorName}.
	 * @param ctx the parse tree
	 */
	void enterOperatorName(@NotNull CQLParser.OperatorNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#operatorName}.
	 * @param ctx the parse tree
	 */
	void exitOperatorName(@NotNull CQLParser.OperatorNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#windowBody}.
	 * @param ctx the parse tree
	 */
	void enterWindowBody(@NotNull CQLParser.WindowBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#windowBody}.
	 * @param ctx the parse tree
	 */
	void exitWindowBody(@NotNull CQLParser.WindowBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#extended}.
	 * @param ctx the parse tree
	 */
	void enterExtended(@NotNull CQLParser.ExtendedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#extended}.
	 * @param ctx the parse tree
	 */
	void exitExtended(@NotNull CQLParser.ExtendedContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#packageNameIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterPackageNameIdentifier(@NotNull CQLParser.PackageNameIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#packageNameIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitPackageNameIdentifier(@NotNull CQLParser.PackageNameIdentifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#logicExpressionNot}.
	 * @param ctx the parse tree
	 */
	void enterLogicExpressionNot(@NotNull CQLParser.LogicExpressionNotContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#logicExpressionNot}.
	 * @param ctx the parse tree
	 */
	void exitLogicExpressionNot(@NotNull CQLParser.LogicExpressionNotContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#confValue}.
	 * @param ctx the parse tree
	 */
	void enterConfValue(@NotNull CQLParser.ConfValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#confValue}.
	 * @param ctx the parse tree
	 */
	void exitConfValue(@NotNull CQLParser.ConfValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constStingValue}.
	 * @param ctx the parse tree
	 */
	void enterConstStingValue(@NotNull CQLParser.ConstStingValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constStingValue}.
	 * @param ctx the parse tree
	 */
	void exitConstStingValue(@NotNull CQLParser.ConstStingValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void enterSelectClause(@NotNull CQLParser.SelectClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectClause}.
	 * @param ctx the parse tree
	 */
	void exitSelectClause(@NotNull CQLParser.SelectClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void enterSetStatement(@NotNull CQLParser.SetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#setStatement}.
	 * @param ctx the parse tree
	 */
	void exitSetStatement(@NotNull CQLParser.SetStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpression(@NotNull CQLParser.AtomExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpression(@NotNull CQLParser.AtomExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#colType}.
	 * @param ctx the parse tree
	 */
	void enterColType(@NotNull CQLParser.ColTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#colType}.
	 * @param ctx the parse tree
	 */
	void exitColType(@NotNull CQLParser.ColTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constFloatValue}.
	 * @param ctx the parse tree
	 */
	void enterConstFloatValue(@NotNull CQLParser.ConstFloatValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constFloatValue}.
	 * @param ctx the parse tree
	 */
	void exitConstFloatValue(@NotNull CQLParser.ConstFloatValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#usingStatement}.
	 * @param ctx the parse tree
	 */
	void enterUsingStatement(@NotNull CQLParser.UsingStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#usingStatement}.
	 * @param ctx the parse tree
	 */
	void exitUsingStatement(@NotNull CQLParser.UsingStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#isForce}.
	 * @param ctx the parse tree
	 */
	void enterIsForce(@NotNull CQLParser.IsForceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#isForce}.
	 * @param ctx the parse tree
	 */
	void exitIsForce(@NotNull CQLParser.IsForceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#serdeProperties}.
	 * @param ctx the parse tree
	 */
	void enterSerdeProperties(@NotNull CQLParser.SerdePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#serdeProperties}.
	 * @param ctx the parse tree
	 */
	void exitSerdeProperties(@NotNull CQLParser.SerdePropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#subQueryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubQueryExpression(@NotNull CQLParser.SubQueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#subQueryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubQueryExpression(@NotNull CQLParser.SubQueryExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#applicationName}.
	 * @param ctx the parse tree
	 */
	void enterApplicationName(@NotNull CQLParser.ApplicationNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#applicationName}.
	 * @param ctx the parse tree
	 */
	void exitApplicationName(@NotNull CQLParser.ApplicationNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void enterHavingClause(@NotNull CQLParser.HavingClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#havingClause}.
	 * @param ctx the parse tree
	 */
	void exitHavingClause(@NotNull CQLParser.HavingClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull CQLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull CQLParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#bitOperator}.
	 * @param ctx the parse tree
	 */
	void enterBitOperator(@NotNull CQLParser.BitOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#bitOperator}.
	 * @param ctx the parse tree
	 */
	void exitBitOperator(@NotNull CQLParser.BitOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void enterFromClause(@NotNull CQLParser.FromClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#fromClause}.
	 * @param ctx the parse tree
	 */
	void exitFromClause(@NotNull CQLParser.FromClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#windowSource}.
	 * @param ctx the parse tree
	 */
	void enterWindowSource(@NotNull CQLParser.WindowSourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#windowSource}.
	 * @param ctx the parse tree
	 */
	void exitWindowSource(@NotNull CQLParser.WindowSourceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamProperties}.
	 * @param ctx the parse tree
	 */
	void enterStreamProperties(@NotNull CQLParser.StreamPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamProperties}.
	 * @param ctx the parse tree
	 */
	void exitStreamProperties(@NotNull CQLParser.StreamPropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#searchCondition}.
	 * @param ctx the parse tree
	 */
	void enterSearchCondition(@NotNull CQLParser.SearchConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#searchCondition}.
	 * @param ctx the parse tree
	 */
	void exitSearchCondition(@NotNull CQLParser.SearchConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void enterSelectItem(@NotNull CQLParser.SelectItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectItem}.
	 * @param ctx the parse tree
	 */
	void exitSelectItem(@NotNull CQLParser.SelectItemContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createOperatorStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateOperatorStatement(@NotNull CQLParser.CreateOperatorStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createOperatorStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateOperatorStatement(@NotNull CQLParser.CreateOperatorStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#joinRigthBody}.
	 * @param ctx the parse tree
	 */
	void enterJoinRigthBody(@NotNull CQLParser.JoinRigthBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#joinRigthBody}.
	 * @param ctx the parse tree
	 */
	void exitJoinRigthBody(@NotNull CQLParser.JoinRigthBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#insertUserOperatorStatement}.
	 * @param ctx the parse tree
	 */
	void enterInsertUserOperatorStatement(@NotNull CQLParser.InsertUserOperatorStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#insertUserOperatorStatement}.
	 * @param ctx the parse tree
	 */
	void exitInsertUserOperatorStatement(@NotNull CQLParser.InsertUserOperatorStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#dataSourceName}.
	 * @param ctx the parse tree
	 */
	void enterDataSourceName(@NotNull CQLParser.DataSourceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#dataSourceName}.
	 * @param ctx the parse tree
	 */
	void exitDataSourceName(@NotNull CQLParser.DataSourceNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#logicExpressionAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicExpressionAnd(@NotNull CQLParser.LogicExpressionAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#logicExpressionAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicExpressionAnd(@NotNull CQLParser.LogicExpressionAndContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceProperties}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceProperties(@NotNull CQLParser.DatasourcePropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceProperties}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceProperties(@NotNull CQLParser.DatasourcePropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeBound}.
	 * @param ctx the parse tree
	 */
	void enterRangeBound(@NotNull CQLParser.RangeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeBound}.
	 * @param ctx the parse tree
	 */
	void exitRangeBound(@NotNull CQLParser.RangeBoundContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#limitRow}.
	 * @param ctx the parse tree
	 */
	void enterLimitRow(@NotNull CQLParser.LimitRowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#limitRow}.
	 * @param ctx the parse tree
	 */
	void exitLimitRow(@NotNull CQLParser.LimitRowContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#ifExists}.
	 * @param ctx the parse tree
	 */
	void enterIfExists(@NotNull CQLParser.IfExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#ifExists}.
	 * @param ctx the parse tree
	 */
	void exitIfExists(@NotNull CQLParser.IfExistsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#serdeClass}.
	 * @param ctx the parse tree
	 */
	void enterSerdeClass(@NotNull CQLParser.SerdeClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#serdeClass}.
	 * @param ctx the parse tree
	 */
	void exitSerdeClass(@NotNull CQLParser.SerdeClassContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createPipeStreamStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreatePipeStreamStatement(@NotNull CQLParser.CreatePipeStreamStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createPipeStreamStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreatePipeStreamStatement(@NotNull CQLParser.CreatePipeStreamStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#showFunctions}.
	 * @param ctx the parse tree
	 */
	void enterShowFunctions(@NotNull CQLParser.ShowFunctionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#showFunctions}.
	 * @param ctx the parse tree
	 */
	void exitShowFunctions(@NotNull CQLParser.ShowFunctionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#equalRelationExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualRelationExpression(@NotNull CQLParser.EqualRelationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#equalRelationExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualRelationExpression(@NotNull CQLParser.EqualRelationExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#windowProperties}.
	 * @param ctx the parse tree
	 */
	void enterWindowProperties(@NotNull CQLParser.WindowPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#windowProperties}.
	 * @param ctx the parse tree
	 */
	void exitWindowProperties(@NotNull CQLParser.WindowPropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeWindow}.
	 * @param ctx the parse tree
	 */
	void enterRangeWindow(@NotNull CQLParser.RangeWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeWindow}.
	 * @param ctx the parse tree
	 */
	void exitRangeWindow(@NotNull CQLParser.RangeWindowContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnName(@NotNull CQLParser.ColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnName(@NotNull CQLParser.ColumnNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#groupByList}.
	 * @param ctx the parse tree
	 */
	void enterGroupByList(@NotNull CQLParser.GroupByListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#groupByList}.
	 * @param ctx the parse tree
	 */
	void exitGroupByList(@NotNull CQLParser.GroupByListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeMinutes}.
	 * @param ctx the parse tree
	 */
	void enterRangeMinutes(@NotNull CQLParser.RangeMinutesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeMinutes}.
	 * @param ctx the parse tree
	 */
	void exitRangeMinutes(@NotNull CQLParser.RangeMinutesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(@NotNull CQLParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(@NotNull CQLParser.WhereClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#dropFunctionStatement}.
	 * @param ctx the parse tree
	 */
	void enterDropFunctionStatement(@NotNull CQLParser.DropFunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#dropFunctionStatement}.
	 * @param ctx the parse tree
	 */
	void exitDropFunctionStatement(@NotNull CQLParser.DropFunctionStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(@NotNull CQLParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(@NotNull CQLParser.ConstantContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamBody}.
	 * @param ctx the parse tree
	 */
	void enterStreamBody(@NotNull CQLParser.StreamBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamBody}.
	 * @param ctx the parse tree
	 */
	void exitStreamBody(@NotNull CQLParser.StreamBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createDataSourceStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateDataSourceStatement(@NotNull CQLParser.CreateDataSourceStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createDataSourceStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateDataSourceStatement(@NotNull CQLParser.CreateDataSourceStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#partitionbyDeterminer}.
	 * @param ctx the parse tree
	 */
	void enterPartitionbyDeterminer(@NotNull CQLParser.PartitionbyDeterminerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#partitionbyDeterminer}.
	 * @param ctx the parse tree
	 */
	void exitPartitionbyDeterminer(@NotNull CQLParser.PartitionbyDeterminerContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#relationOperator}.
	 * @param ctx the parse tree
	 */
	void enterRelationOperator(@NotNull CQLParser.RelationOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#relationOperator}.
	 * @param ctx the parse tree
	 */
	void exitRelationOperator(@NotNull CQLParser.RelationOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#limitAll}.
	 * @param ctx the parse tree
	 */
	void enterLimitAll(@NotNull CQLParser.LimitAllContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#limitAll}.
	 * @param ctx the parse tree
	 */
	void exitLimitAll(@NotNull CQLParser.LimitAllContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#confName}.
	 * @param ctx the parse tree
	 */
	void enterConfName(@NotNull CQLParser.ConfNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#confName}.
	 * @param ctx the parse tree
	 */
	void exitConfName(@NotNull CQLParser.ConfNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(@NotNull CQLParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(@NotNull CQLParser.CastExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnNameType}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameType(@NotNull CQLParser.ColumnNameTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnNameType}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameType(@NotNull CQLParser.ColumnNameTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(@NotNull CQLParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(@NotNull CQLParser.FunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void enterGroupByClause(@NotNull CQLParser.GroupByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#groupByClause}.
	 * @param ctx the parse tree
	 */
	void exitGroupByClause(@NotNull CQLParser.GroupByClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeTime}.
	 * @param ctx the parse tree
	 */
	void enterRangeTime(@NotNull CQLParser.RangeTimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeTime}.
	 * @param ctx the parse tree
	 */
	void exitRangeTime(@NotNull CQLParser.RangeTimeContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectAlias}.
	 * @param ctx the parse tree
	 */
	void enterSelectAlias(@NotNull CQLParser.SelectAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectAlias}.
	 * @param ctx the parse tree
	 */
	void exitSelectAlias(@NotNull CQLParser.SelectAliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constIntegerValue}.
	 * @param ctx the parse tree
	 */
	void enterConstIntegerValue(@NotNull CQLParser.ConstIntegerValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constIntegerValue}.
	 * @param ctx the parse tree
	 */
	void exitConstIntegerValue(@NotNull CQLParser.ConstIntegerValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#windowName}.
	 * @param ctx the parse tree
	 */
	void enterWindowName(@NotNull CQLParser.WindowNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#windowName}.
	 * @param ctx the parse tree
	 */
	void exitWindowName(@NotNull CQLParser.WindowNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectStatement(@NotNull CQLParser.SelectStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectStatement(@NotNull CQLParser.SelectStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sinkClause}.
	 * @param ctx the parse tree
	 */
	void enterSinkClause(@NotNull CQLParser.SinkClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sinkClause}.
	 * @param ctx the parse tree
	 */
	void exitSinkClause(@NotNull CQLParser.SinkClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#getStatement}.
	 * @param ctx the parse tree
	 */
	void enterGetStatement(@NotNull CQLParser.GetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#getStatement}.
	 * @param ctx the parse tree
	 */
	void exitGetStatement(@NotNull CQLParser.GetStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#identifierNot}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierNot(@NotNull CQLParser.IdentifierNotContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#identifierNot}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierNot(@NotNull CQLParser.IdentifierNotContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceBody}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceBody(@NotNull CQLParser.DatasourceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceBody}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceBody(@NotNull CQLParser.DatasourceBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#showApplications}.
	 * @param ctx the parse tree
	 */
	void enterShowApplications(@NotNull CQLParser.ShowApplicationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#showApplications}.
	 * @param ctx the parse tree
	 */
	void exitShowApplications(@NotNull CQLParser.ShowApplicationsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#insertClause}.
	 * @param ctx the parse tree
	 */
	void enterInsertClause(@NotNull CQLParser.InsertClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#insertClause}.
	 * @param ctx the parse tree
	 */
	void exitInsertClause(@NotNull CQLParser.InsertClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#submitApplication}.
	 * @param ctx the parse tree
	 */
	void enterSubmitApplication(@NotNull CQLParser.SubmitApplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#submitApplication}.
	 * @param ctx the parse tree
	 */
	void exitSubmitApplication(@NotNull CQLParser.SubmitApplicationContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#expressions}.
	 * @param ctx the parse tree
	 */
	void enterExpressions(@NotNull CQLParser.ExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#expressions}.
	 * @param ctx the parse tree
	 */
	void exitExpressions(@NotNull CQLParser.ExpressionsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#binaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(@NotNull CQLParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#binaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(@NotNull CQLParser.BinaryExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#explainStatement}.
	 * @param ctx the parse tree
	 */
	void enterExplainStatement(@NotNull CQLParser.ExplainStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#explainStatement}.
	 * @param ctx the parse tree
	 */
	void exitExplainStatement(@NotNull CQLParser.ExplainStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createFunctionStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateFunctionStatement(@NotNull CQLParser.CreateFunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createFunctionStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateFunctionStatement(@NotNull CQLParser.CreateFunctionStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#joinSource}.
	 * @param ctx the parse tree
	 */
	void enterJoinSource(@NotNull CQLParser.JoinSourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#joinSource}.
	 * @param ctx the parse tree
	 */
	void exitJoinSource(@NotNull CQLParser.JoinSourceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceQueryArguments}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceQueryArguments(@NotNull CQLParser.DatasourceQueryArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceQueryArguments}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceQueryArguments(@NotNull CQLParser.DatasourceQueryArgumentsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#leftJoin}.
	 * @param ctx the parse tree
	 */
	void enterLeftJoin(@NotNull CQLParser.LeftJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#leftJoin}.
	 * @param ctx the parse tree
	 */
	void exitLeftJoin(@NotNull CQLParser.LeftJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(@NotNull CQLParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(@NotNull CQLParser.BooleanValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#unidirection}.
	 * @param ctx the parse tree
	 */
	void enterUnidirection(@NotNull CQLParser.UnidirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#unidirection}.
	 * @param ctx the parse tree
	 */
	void exitUnidirection(@NotNull CQLParser.UnidirectionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#groupByExpression}.
	 * @param ctx the parse tree
	 */
	void enterGroupByExpression(@NotNull CQLParser.GroupByExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#groupByExpression}.
	 * @param ctx the parse tree
	 */
	void exitGroupByExpression(@NotNull CQLParser.GroupByExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sourceAlias}.
	 * @param ctx the parse tree
	 */
	void enterSourceAlias(@NotNull CQLParser.SourceAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sourceAlias}.
	 * @param ctx the parse tree
	 */
	void exitSourceAlias(@NotNull CQLParser.SourceAliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rowsWindow}.
	 * @param ctx the parse tree
	 */
	void enterRowsWindow(@NotNull CQLParser.RowsWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rowsWindow}.
	 * @param ctx the parse tree
	 */
	void exitRowsWindow(@NotNull CQLParser.RowsWindowContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnOrder}.
	 * @param ctx the parse tree
	 */
	void enterColumnOrder(@NotNull CQLParser.ColumnOrderContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnOrder}.
	 * @param ctx the parse tree
	 */
	void exitColumnOrder(@NotNull CQLParser.ColumnOrderContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#datasourceArguments}.
	 * @param ctx the parse tree
	 */
	void enterDatasourceArguments(@NotNull CQLParser.DatasourceArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#datasourceArguments}.
	 * @param ctx the parse tree
	 */
	void exitDatasourceArguments(@NotNull CQLParser.DatasourceArgumentsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeUnBound}.
	 * @param ctx the parse tree
	 */
	void enterRangeUnBound(@NotNull CQLParser.RangeUnBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeUnBound}.
	 * @param ctx the parse tree
	 */
	void exitRangeUnBound(@NotNull CQLParser.RangeUnBoundContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#distinct}.
	 * @param ctx the parse tree
	 */
	void enterDistinct(@NotNull CQLParser.DistinctContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#distinct}.
	 * @param ctx the parse tree
	 */
	void exitDistinct(@NotNull CQLParser.DistinctContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamSource}.
	 * @param ctx the parse tree
	 */
	void enterStreamSource(@NotNull CQLParser.StreamSourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamSource}.
	 * @param ctx the parse tree
	 */
	void exitStreamSource(@NotNull CQLParser.StreamSourceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#arithmeticPlusOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticPlusOperator(@NotNull CQLParser.ArithmeticPlusOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#arithmeticPlusOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticPlusOperator(@NotNull CQLParser.ArithmeticPlusOperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sinkProperties}.
	 * @param ctx the parse tree
	 */
	void enterSinkProperties(@NotNull CQLParser.SinkPropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sinkProperties}.
	 * @param ctx the parse tree
	 */
	void exitSinkProperties(@NotNull CQLParser.SinkPropertiesContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#crossJoin}.
	 * @param ctx the parse tree
	 */
	void enterCrossJoin(@NotNull CQLParser.CrossJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#crossJoin}.
	 * @param ctx the parse tree
	 */
	void exitCrossJoin(@NotNull CQLParser.CrossJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectExpression}.
	 * @param ctx the parse tree
	 */
	void enterSelectExpression(@NotNull CQLParser.SelectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectExpression}.
	 * @param ctx the parse tree
	 */
	void exitSelectExpression(@NotNull CQLParser.SelectExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#naturalJoin}.
	 * @param ctx the parse tree
	 */
	void enterNaturalJoin(@NotNull CQLParser.NaturalJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#naturalJoin}.
	 * @param ctx the parse tree
	 */
	void exitNaturalJoin(@NotNull CQLParser.NaturalJoinContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeSeconds}.
	 * @param ctx the parse tree
	 */
	void enterRangeSeconds(@NotNull CQLParser.RangeSecondsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeSeconds}.
	 * @param ctx the parse tree
	 */
	void exitRangeSeconds(@NotNull CQLParser.RangeSecondsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#columnNameOrderList}.
	 * @param ctx the parse tree
	 */
	void enterColumnNameOrderList(@NotNull CQLParser.ColumnNameOrderListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#columnNameOrderList}.
	 * @param ctx the parse tree
	 */
	void exitColumnNameOrderList(@NotNull CQLParser.ColumnNameOrderListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull CQLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull CQLParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#addJARStatement}.
	 * @param ctx the parse tree
	 */
	void enterAddJARStatement(@NotNull CQLParser.AddJARStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#addJARStatement}.
	 * @param ctx the parse tree
	 */
	void exitAddJARStatement(@NotNull CQLParser.AddJARStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#constBigDecimalValue}.
	 * @param ctx the parse tree
	 */
	void enterConstBigDecimalValue(@NotNull CQLParser.ConstBigDecimalValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#constBigDecimalValue}.
	 * @param ctx the parse tree
	 */
	void exitConstBigDecimalValue(@NotNull CQLParser.ConstBigDecimalValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#subQuerySource}.
	 * @param ctx the parse tree
	 */
	void enterSubQuerySource(@NotNull CQLParser.SubQuerySourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#subQuerySource}.
	 * @param ctx the parse tree
	 */
	void exitSubQuerySource(@NotNull CQLParser.SubQuerySourceContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(@NotNull CQLParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(@NotNull CQLParser.PrimitiveTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#addFileStatement}.
	 * @param ctx the parse tree
	 */
	void enterAddFileStatement(@NotNull CQLParser.AddFileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#addFileStatement}.
	 * @param ctx the parse tree
	 */
	void exitAddFileStatement(@NotNull CQLParser.AddFileStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#parallelClause}.
	 * @param ctx the parse tree
	 */
	void enterParallelClause(@NotNull CQLParser.ParallelClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#parallelClause}.
	 * @param ctx the parse tree
	 */
	void exitParallelClause(@NotNull CQLParser.ParallelClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#createOutputStreamStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateOutputStreamStatement(@NotNull CQLParser.CreateOutputStreamStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#createOutputStreamStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateOutputStreamStatement(@NotNull CQLParser.CreateOutputStreamStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(@NotNull CQLParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(@NotNull CQLParser.FunctionNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoadStatement(@NotNull CQLParser.LoadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoadStatement(@NotNull CQLParser.LoadStatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamAlias}.
	 * @param ctx the parse tree
	 */
	void enterStreamAlias(@NotNull CQLParser.StreamAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamAlias}.
	 * @param ctx the parse tree
	 */
	void exitStreamAlias(@NotNull CQLParser.StreamAliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void enterLimitClause(@NotNull CQLParser.LimitClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#limitClause}.
	 * @param ctx the parse tree
	 */
	void exitLimitClause(@NotNull CQLParser.LimitClauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeHour}.
	 * @param ctx the parse tree
	 */
	void enterRangeHour(@NotNull CQLParser.RangeHourContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeHour}.
	 * @param ctx the parse tree
	 */
	void exitRangeHour(@NotNull CQLParser.RangeHourContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#cqlIdentifier}.
	 * @param ctx the parse tree
	 */
	void enterCqlIdentifier(@NotNull CQLParser.CqlIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#cqlIdentifier}.
	 * @param ctx the parse tree
	 */
	void exitCqlIdentifier(@NotNull CQLParser.CqlIdentifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamName}.
	 * @param ctx the parse tree
	 */
	void enterStreamName(@NotNull CQLParser.StreamNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamName}.
	 * @param ctx the parse tree
	 */
	void exitStreamName(@NotNull CQLParser.StreamNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#dropApplication}.
	 * @param ctx the parse tree
	 */
	void enterDropApplication(@NotNull CQLParser.DropApplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#dropApplication}.
	 * @param ctx the parse tree
	 */
	void exitDropApplication(@NotNull CQLParser.DropApplicationContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#arithmeticPlusMinusExpression}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticPlusMinusExpression(@NotNull CQLParser.ArithmeticPlusMinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#arithmeticPlusMinusExpression}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticPlusMinusExpression(@NotNull CQLParser.ArithmeticPlusMinusExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#selectList}.
	 * @param ctx the parse tree
	 */
	void enterSelectList(@NotNull CQLParser.SelectListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#selectList}.
	 * @param ctx the parse tree
	 */
	void exitSelectList(@NotNull CQLParser.SelectListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#streamAllColumns}.
	 * @param ctx the parse tree
	 */
	void enterStreamAllColumns(@NotNull CQLParser.StreamAllColumnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#streamAllColumns}.
	 * @param ctx the parse tree
	 */
	void exitStreamAllColumns(@NotNull CQLParser.StreamAllColumnsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#commentString}.
	 * @param ctx the parse tree
	 */
	void enterCommentString(@NotNull CQLParser.CommentStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#commentString}.
	 * @param ctx the parse tree
	 */
	void exitCommentString(@NotNull CQLParser.CommentStringContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#singleAlias}.
	 * @param ctx the parse tree
	 */
	void enterSingleAlias(@NotNull CQLParser.SingleAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#singleAlias}.
	 * @param ctx the parse tree
	 */
	void exitSingleAlias(@NotNull CQLParser.SingleAliasContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#bitExpression}.
	 * @param ctx the parse tree
	 */
	void enterBitExpression(@NotNull CQLParser.BitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#bitExpression}.
	 * @param ctx the parse tree
	 */
	void exitBitExpression(@NotNull CQLParser.BitExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#rangeMilliSeconds}.
	 * @param ctx the parse tree
	 */
	void enterRangeMilliSeconds(@NotNull CQLParser.RangeMilliSecondsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#rangeMilliSeconds}.
	 * @param ctx the parse tree
	 */
	void exitRangeMilliSeconds(@NotNull CQLParser.RangeMilliSecondsContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#sinkDefine}.
	 * @param ctx the parse tree
	 */
	void enterSinkDefine(@NotNull CQLParser.SinkDefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#sinkDefine}.
	 * @param ctx the parse tree
	 */
	void exitSinkDefine(@NotNull CQLParser.SinkDefineContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(@NotNull CQLParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(@NotNull CQLParser.CommentContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#filterBeforeWindow}.
	 * @param ctx the parse tree
	 */
	void enterFilterBeforeWindow(@NotNull CQLParser.FilterBeforeWindowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#filterBeforeWindow}.
	 * @param ctx the parse tree
	 */
	void exitFilterBeforeWindow(@NotNull CQLParser.FilterBeforeWindowContext ctx);

	/**
	 * Enter a parse tree produced by {@link CQLParser#arithmeticStarOperator}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticStarOperator(@NotNull CQLParser.ArithmeticStarOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CQLParser#arithmeticStarOperator}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticStarOperator(@NotNull CQLParser.ArithmeticStarOperatorContext ctx);
}