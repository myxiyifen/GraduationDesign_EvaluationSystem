package com.gdes.GDES.controller;

import com.gdes.GDES.model.*;
import com.gdes.GDES.service.*;
import myclass.ProfessionalCompetenceEvaluation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/evaluation/")
public class EvaluationController {
    @Resource
    private KnowledgepointService knowledgepointService;

    @Resource
    private QuestionspointService questionspointService;

    @Resource
    private ScoredetailService scoredetailService;

    @Resource
    private LatestabilityscoreService latestabilityscoreService;

    @Resource
    private MajorService majorService;

    @Resource
    private ProfessionalabilitypointrequirementsService professionalabilitypointrequirementsService;

    @RequestMapping("execut")
    public String executing() throws Exception {
        //假设当前是五道试题
        String[] ique = {"1", "10ce6b128195427fae43bd7a820effa5", "9061c8c7eeef4a19972db7215b5da6a9", "998ce548e5fc44e5a1d3ad477d577ee0", "f3ed67c494bf4f7da11881d1bb07c64c"};
        double[][] score_questions = {{1, 1, 5, 5, 5}}; //题目本来的分值
        double[][] score_student = {{1, 1, 4, 3, 5}}; //学生五道试题的得分
        String teacherid = "1"; //教师id
        String studentid = "631406010217"; //学生id
        String erid = "ssss"; //评测记录id

        int a = ique.length; //学生提交试题的个数
        int b = Integer.parseInt(Long.toString(knowledgepointService.getCount()));
        double[][] question_knowledge = new double[a][b+1]; //试题与知识点的矩阵
        for(int i=0;i<a;i++) {
            List<Questionspoint> questionspoints = questionspointService.queryByQuestionId(ique[i]);
            for(Questionspoint qp: questionspoints) {
                int kpid = qp.getIdKp(); //知识点id
                double kppro = Double.parseDouble(qp.getProportionQp());
                question_knowledge[i][kpid] = kppro;
            }
        }

        double[][] knowledge_ability = new double[b+1][83]; //知识与能力点矩阵
        for(int j=1;j<83;j++) {
            List<Knowledgepoint> knowledgePointList = knowledgepointService.queryByAbilityId(j);
            for(Knowledgepoint kp: knowledgePointList) {
                int kpid = kp.getIdKp(); //知识点Id
                double kppro = Double.parseDouble(kp.getProportionKp());
                knowledge_ability[kpid][j] = kppro;
            }
        }

        //所有试题原本对应的能力点得分（相当于每道题都已满分算出来能力点应该得的分数）
        double[][] score_ability = ProfessionalCompetenceEvaluation.AbilityScore(question_knowledge, knowledge_ability, score_questions);
        //学生能力点得分，按每个能力点100分计算
        double[][] score_ability_student = ProfessionalCompetenceEvaluation.AbilityScore(question_knowledge, knowledge_ability, score_student);
        //存到一维数组（能力点得分）
        double[] result = new double[83];
        for(int i=0;i<score_ability[0].length;i++) {
            result[i] = 0;
            if(score_ability_student[0][i]!=0) {
                result[i] = ProfessionalCompetenceEvaluation.roundOffTwoDecimalPlaces((score_ability_student[0][i]/score_ability[0][i])*100);
            }
        }

        /**
         * 学生专业所属专业大类要求下的得分
         */
        List<Major> majors = majorService.queryByMajorId("01");
        int mbid = majors.get(0).getIdMb(); //专业大类id
        List<Professionalabilitypointrequirements> professionalAbilityPointRequirements = professionalabilitypointrequirementsService.queryByMajorBId(mbid);
        int mi = 1;
        double[] majorrequire = new double[83];
        for(Professionalabilitypointrequirements p: professionalAbilityPointRequirements) {
            double con = Double.parseDouble(p.getConversionratioPapr()); //转换的比例
            majorrequire[mi] = con;
            result[mi] = ProfessionalCompetenceEvaluation.roundOffTwoDecimalPlaces(result[mi]*con);
            mi++;
        }
        //得到试题和能力点的得分占比,每道试题能力点加起来为1
        double[][] question_ability = ProfessionalCompetenceEvaluation.ColumnRatioByRow(
                ProfessionalCompetenceEvaluation.MatrixMultiplication(question_knowledge, knowledge_ability));
        //每道试题能力点的得分
        for(int i=0;i<question_ability.length;i++) {
            for(int j=0;j<question_ability[0].length; j++) {
                if(question_ability[i][j]!=0) {
                    question_ability[i][j] = (score_student[0][i]/score_questions[0][i])*majorrequire[j]*100;
                }
            }
        }
        /**
         * 插入得分详情
         */
        /*for(int i=0;i<question_ability.length;i++) {
            for(int j=0;j<question_ability[0].length;j++) {
                if(question_ability[i][j]!=0) {
                    Scoredetail scoredetail = new Scoredetail();
                    scoredetail.setIdT(teacherid);
                    scoredetail.setIdS(studentid);
                    scoredetail.setIdEr(erid);
                    scoredetail.setIdQ(ique[i]);
                    scoredetail.setIdAp(j);
                    scoredetail.setScoreSd(Double.toString(question_ability[i][j]));
                    scoredetail.setTimeSd(nowTime());
                    scoredetailService.addScoredetail(scoredetail); //插入数据库
                }
            }
        }*/

        /**
         * 插入最新能力得分
         */
        List<Integer> apidList = scoredetailService.getApIdNotRepeat(studentid);
        /*for(int i=0;i<result.length;i++) {
            if(result[i]!=0) {
                Latestabilityscore latestabilityscore = new Latestabilityscore();
                latestabilityscore.setIdS(studentid);
                latestabilityscore.setIdAp(i);
                latestabilityscore.setScoreLas(Double.toString(result[i]));
                latestabilityscore.setTimeLas(nowTime());
                latestabilityscore.setProportionLas("这里是百分比");
                latestabilityscoreService.addLatestabilityscore(latestabilityscore);
            }
        }*/

        return "";
    }

    /**
     * 获取当前时间
     * @return String
     */
    public String nowTime() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(now);
    }

}
