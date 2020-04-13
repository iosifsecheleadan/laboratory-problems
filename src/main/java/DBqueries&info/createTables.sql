create table Student (
    ID              bigint  primary key,
    serialNumber    text    not null,
    name            text    not null,
    "group"         integer not null
);

create table LabProblem (
    ID              bigint  primary key ,
    problemNumber   int     not null,
    name            text    not null,
    description     text    not null
);

create table Assignment (
    ID              bigint  primary key,
    studentID       bigint  references Student (ID),
    problemID       bigint  references LabProblem (ID)
);
