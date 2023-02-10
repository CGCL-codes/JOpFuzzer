import pandas as pd
import os
import math
import re
import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as stats
import sys
import wget

# Trend graph of the change in the number of HotSpot and Compiler
def num_proportion():
    day = []
    day1 = []
    allnum = []
    comnum = []
    pronum = []
    alldict = {}
    comdict = {}
    ticks = []
    for i in range(2000, 2022):
        for j in range(1, 13):
            a = str(i) + '-' + str(j)
            day1.append(a)
    day1.extend(['2022-1', '2022-2', '2022-3'])

    for i in range(0, len(day1), 3):
        day.append(day1[i])
    # print(day)

    for i in range(0, len(day), 17):
        ticks.append(day[i])
    # ticks.append('2022-3')
    for rootpath, dirpath, filepath in os.walk('../JDK_data'):
        for ifile in filepath:
            path = os.path.join(rootpath, ifile)
            df = pd.read_csv(path)

            for index, row in df.iterrows():
                if row['Component/s'] != 'hotspot':
                    continue
                first = row['Created'].split(' ')[0]
                matchobj = re.match('(.+)-(.+)-(.+)', first)
                fy = int(matchobj.group(1))
                fm = int(matchobj.group(2))
                fd = int(matchobj.group(3))

                if fm < 4:
                    time = str(fy) + '-' + str(1)
                elif fm < 7:
                    time = str(fy) + '-' + str(4)
                elif fm < 10:
                    time = str(fy) + '-' + str(7)
                else:
                    time = str(fy) + '-' + str(10)

                if time in alldict.keys():
                    alldict[time] = alldict[time] + 1
                else:
                    alldict[time] = 1

    for rootpath, dirpath, filepath in os.walk('../Compiler_data'):
        for ifile in filepath:
            path = os.path.join(rootpath, ifile)
            df = pd.read_csv(path)

            for index, row in df.iterrows():
                first = row['Created'].split(' ')[0]
                matchobj = re.match('(.+)-(.+)-(.+)', first)
                fy = int(matchobj.group(1))
                fm = int(matchobj.group(2))
                fd = int(matchobj.group(3))

                if fm < 4:
                    time = str(fy) + '-' + str(1)
                elif fm < 7:
                    time = str(fy) + '-' + str(4)
                elif fm < 10:
                    time = str(fy) + '-' + str(7)
                else:
                    time = str(fy) + '-' + str(10)

                if time in comdict.keys():
                    comdict[time] = comdict[time] + 1
                else:
                    comdict[time] = 1

    for i in day:
        if i in alldict.keys():
            allnum.append(alldict[i])
        else:
            allnum.append(0)

        if i in comdict.keys():
            comnum.append(comdict[i])
            pronum.append(comdict[i] / alldict[i])
        else:
            comnum.append(0)

    fig, ax1 = plt.subplots()
    ax2 = ax1.twinx()
    ax1.plot(day, allnum, '#4169E1')
    ax1.set_ylabel('Number', color='#4169E1')
    ax2.plot(day, pronum, 'r--')
    ax2.set_ylabel('Proportion', color='r')
    plt.grid(axis='both', ls='--')
    plt.xticks(ticks, rotation=45)
    plt.tight_layout()
    plt.show()
    '''

    with open('allnum_proportion.csv','w') as f:
        for i in allnum:
            f.write(str(i)+',')
        f.write('\n')
        for i in pronum:
            f.write(str(i)+',')

    '''


def draw_priorty():
    ps1 = []
    ps2 = []
    ps3 = []
    ps4 = []
    ps5 = []
    # xbar = ['JIT Compiler','Runtime','All','GC','Other','JFR','SVC','JVMTI']
    xbar = ['compiler', 'runtime', 'all', 'gc', 'other', 'jfr', 'svc', 'jvmti']
    ind = [8, 7, 6, 5, 4, 3, 2, 1]

    for i in xbar:
        a = 0
        b = 0
        c = 0
        d = 0
        e = 0

        if i == 'all':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] != 'hotspot':
                            continue
                        if row['Priority'] == 'P1':
                            a = a + 1
                        elif row['Priority'] == 'P2':
                            b = b + 1
                        elif row['Priority'] == 'P3':
                            c = c + 1
                        elif row['Priority'] == 'P4':
                            d = d + 1
                        elif row['Priority'] == 'P5':
                            e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)
            continue

        if i == 'compiler':
            for rootpath, dirpath, filepath in os.walk('../Compiler_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Priority'] == 'P1':
                            a = a + 1
                        elif row['Priority'] == 'P2':
                            b = b + 1
                        elif row['Priority'] == 'P3':
                            c = c + 1
                        elif row['Priority'] == 'P4':
                            d = d + 1
                        elif row['Priority'] == 'P5':
                            e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)
            continue

        if i == 'gc':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('5d640af4' in str(row['Custom field (Subcomponent)'])) or (
                                '2e4eb2bc' in str(row['Custom field (Subcomponent)'])) or (
                                        '400daccd' in str(row['Custom field (Subcomponent)'])) or (
                                        '723b1f03' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

        if i == 'jfr':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('586bfeff' in str(row['Custom field (Subcomponent)'])) or (
                                '28f62291' in str(row['Custom field (Subcomponent)'])) or (
                                        '646b6bfe' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

        if i == 'jvmti':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('76518167' in str(row['Custom field (Subcomponent)'])) or (
                                '3f3d9c09' in str(row['Custom field (Subcomponent)'])) or (
                                        '617d0cb8' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

        if i == 'runtime':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('216f1fe7' in str(row['Custom field (Subcomponent)'])) or (
                                '368c800d' in str(row['Custom field (Subcomponent)'])) or (
                                        'a4a38a9' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

        if i == 'svc':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('1a7ea3f0' in str(row['Custom field (Subcomponent)'])) or (
                                '22ed5982' in str(row['Custom field (Subcomponent)'])) or (
                                        '326b62a5' in str(row['Custom field (Subcomponent)'])) or (
                                        '391247c' in str(row['Custom field (Subcomponent)'])) or (
                                        '45a85b32' in str(row['Custom field (Subcomponent)'])) or (
                                        '4fe221f' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

        if i == 'other':
            for rootpath, dirpath, filepath in os.walk('../JDK_data'):
                for ifile in filepath:
                    path = os.path.join(rootpath, ifile)
                    df = pd.read_csv(path)

                    for index, row in df.iterrows():
                        if row['Component/s'] == 'hotspot' and (
                                ('45f33599' in str(row['Custom field (Subcomponent)'])) or (
                                '495fd1b8' in str(row['Custom field (Subcomponent)'])) or (
                                        '1f43f15b' in str(row['Custom field (Subcomponent)'])) or (
                                        '5718f51f' in str(row['Custom field (Subcomponent)'])) or (
                                        '447ce8b4' in str(row['Custom field (Subcomponent)']))):
                            if row['Priority'] == 'P1':
                                a = a + 1
                            elif row['Priority'] == 'P2':
                                b = b + 1
                            elif row['Priority'] == 'P3':
                                c = c + 1
                            elif row['Priority'] == 'P4':
                                d = d + 1
                            elif row['Priority'] == 'P5':
                                e = e + 1
            total = a + b + c + d + e
            ps1.append(a / total)
            ps2.append(b / total)
            ps3.append(c / total)
            ps4.append(d / total)
            ps5.append(e / total)

    print(ps1, ps2, ps3, ps4, ps5)
    '''
    ps1 = [0.0845649516612957, 0.05970975961005871, 0.05951730063266422, 0.040411646586345384, 0.030985915492957747, 0.025682182985553772, 0.015037593984962405, 0.010771992818671455] 
    ps2 = [0.2130236692965885, 0.1758059155865736, 0.18808091853471842, 0.21987951807228914, 0.09577464788732394, 0.1332263242375602, 0.10751879699248121, 0.13913824057450627] 
    ps3 = [0.351927991999111, 0.30962667552896866, 0.3300788877606811, 0.30220883534136544, 0.2563380281690141, 0.420545746388443, 0.3661654135338346, 0.37881508078994613] 
    ps4 = [0.32559173241471273, 0.43425279716406334, 0.39951573849878935, 0.40763052208835343, 0.6056338028169014, 0.40930979133226325, 0.49097744360902257, 0.45691202872531417] 
    ps5 = [0.024891654628292032, 0.02060485211033566, 0.02280715457314692, 0.029869477911646587, 0.011267605633802818, 0.011235955056179775, 0.02030075187969925, 0.01436265709156194]
    '''
    ps11 = np.array(ps1)
    ps22 = np.array(ps2)
    ps33 = np.array(ps3)
    ps44 = np.array(ps4)
    ps55 = np.array(ps5)
    plt.figure(figsize=(5, 2))
    plt.bar(x=ps22 + ps33 + ps44 + ps11, width=ps55, height=0.7, label='P5', bottom=ind, color='#D3D3D3',
            orientation="horizontal")
    plt.bar(x=ps33 + ps22 + ps11, width=ps44, height=0.7, label='P4', bottom=ind, color='#A9A9A9',
            orientation="horizontal")
    plt.bar(x=ps22 + ps11, width=ps33, height=0.7, label='P3', bottom=ind, color='#808080', orientation="horizontal")
    plt.bar(x=ps11, width=ps22, height=0.7, label='P2', bottom=ind, color='#696969', orientation="horizontal")
    plt.bar(x=0, bottom=ind, width=ps11, height=0.7, label='P1', color='#000000', orientation="horizontal")
    # plt.xticks(ind, xbar, rotation=20,horizontalalignment='right',weight='bold',size=11)
    plt.xticks(size=10, weight='bold')
    plt.yticks(ind, xbar, weight='bold', size=10)
    # plt.rcParams.update({weight':'bold'})
    plt.legend(bbox_to_anchor=(1.03, 0), loc=3, borderaxespad=0, prop={'weight': 'bold'})
    plt.gcf().subplots_adjust(left=0.1, right=0.85)
    plt.tight_layout()
    plt.show()


def gethelp():
    para = ('   --priorty       get distribution of priorty\n'
            '   --duration      get distribugtion of duration\n'
            '   --resolution    get distribugtion of resolution\n'
            '   --pd            get distribugtion of between priorty and duration day \n'
            '   --duplicate     get distribugtion of duplicate \n'
            '   --fixratio      get distribugtion of fix ratio \n'
            '   --changefile    get distribugtion of changed file number \n'
            '   --deleline      get distribugtion of deleted line number \n'
            '   --insline       get distribugtion of inserted line number \n'
            '   --affectversion get distribugtion of affected versions number \n'
            '   --comment       get distribugtion of comment number \n'
            '   --total         get Trend graph of totalnum and proportion\n')
    print(para)


if __name__ == '__main__':
    if len(sys.argv) < 2:
        arg = ''
    else:
        arg = sys.argv[1]
        wget.download("1")
    if 'priorty' in arg:
        draw_priorty()
    elif 'total' in arg:
        num_proportion()
    else:
        gethelp()
