INSERT INTO topics
VALUES (1, 'Java'),
       (2, 'JS');
INSERT INTO questions
VALUES (1, 'QuestionJava1', 'Easy', 2, 1),
       (2, 'QuestionJava2', 'Hard', 10, 1),
       (3, 'QuestionJava3', 'Middle', 5, 1),
       (4, 'QuestionJS1', 'Hard', 10, 2),
       (5, 'QuestionJS2', 'Middle', 5, 2),
       (6, 'QuestionJS6', 'Easy', 2, 2);
INSERT INTO answers
VALUES (1, 'AnswerJava1', 1, true),
       (2, 'AnswerJava2', 2, true),
       (3, 'AnswerJava3', 3, false),
       (4, 'AnswerJS1', 4, true),
       (5, 'AnswerJS2', 5, false),
       (6, 'AnswerJS3', 6, true);