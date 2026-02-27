import {
  ArrowRight,
  CornerDownRight,
  User,
  Server,
  Database,
  Globe,
  Smartphone,
  Monitor,
  Cloud,
  Code,
  Mail,
  ShoppingCart,
  CreditCard,
  FileText,
  Settings,
} from "lucide-react";
import { useRef } from "react";

import {
  PARTICIPANT_WIDTH,
  PARTICIPANT_SPACING,
  MESSAGE_HEIGHT,
  HEADER_HEIGHT,
  LIFELINE_TOP,
} from "./constants";

export interface Participant {
  id: string;
  name: string;
  icon?: string;
}

export interface Message {
  id: string;
  from: string;
  to: string;
  text: string;
  type: "sync" | "async" | "return" | "self";
}

interface SequenceDiagramProps {
  participants: Participant[];
  messages: Message[];
  onMessageClick?: (message: Message) => void;
  svgRef?: React.RefObject<SVGSVGElement>;
}

export function SequenceDiagram({
  participants,
  messages,
  onMessageClick,
  svgRef,
}: SequenceDiagramProps) {
  const getParticipantX = (participantId: string) => {
    const index = participants.findIndex(
      (p) => p.id === participantId,
    );

    // index가 -1일 경우(참여자가 삭제된 경우 등)를 위한 방어 코드
    if (index === -1) return 0;

    return (
      index * (PARTICIPANT_WIDTH + PARTICIPANT_SPACING) +
      PARTICIPANT_WIDTH / 2
    );
  };

  const getIconComponent = (iconName?: string) => {
    const iconMap: Record<string, typeof User> = {
      user: User,
      server: Server,
      database: Database,
      globe: Globe,
      smartphone: Smartphone,
      monitor: Monitor,
      cloud: Cloud,
      code: Code,
      mail: Mail,
      "shopping-cart": ShoppingCart,
      "credit-card": CreditCard,
      "file-text": FileText,
      settings: Settings,
    };
    return iconName ? iconMap[iconName] : null;
  };

  const totalWidth =
    participants.length *
      (PARTICIPANT_WIDTH + PARTICIPANT_SPACING) +
    100;
  const totalHeight =
    HEADER_HEIGHT + messages.length * MESSAGE_HEIGHT + 100;

  const renderArrow = (
    message: Message,
    fromX: number,
    toX: number,
    y: number,
    index: number,
  ) => {
    const isSelfCall = message.from === message.to;

    if (isSelfCall) {
      const loopWidth = PARTICIPANT_WIDTH / 3;
      return (
        <g>
          {/* Message number at the end of self-call */}
          <circle
            cx={fromX + loopWidth + 15}
            cy={y + MESSAGE_HEIGHT * 0.6}
            r="10"
            fill="#3b82f6"
          />
          <text
            x={fromX + loopWidth + 15}
            y={y + MESSAGE_HEIGHT * 0.6 + 4}
            fill="white"
            textAnchor="middle"
            className="text-xs fill-white font-semibold"
          >
            {index + 1}
          </text>
          {/* Self-call arrow */}
          <path
            d={`M ${fromX} ${y} 
                L ${fromX + loopWidth} ${y}
                L ${fromX + loopWidth} ${y + MESSAGE_HEIGHT * 0.6}
                L ${fromX} ${y + MESSAGE_HEIGHT * 0.6}`}
            fill="none"
            stroke="#475569"
            strokeWidth="2"
            markerEnd="url(#arrowhead)"
          />
          {/* Message text */}
          <text
            x={fromX + loopWidth + 8}
            y={y + MESSAGE_HEIGHT * 0.3}
            className="text-sm fill-slate-700"
          >
            {message.text}
          </text>
        </g>
      );
    }

    const isReturn = message.type === "return";
    const direction = fromX < toX ? 1 : -1;

    const textX = (fromX + toX) / 2;
    const textY = y - 20; // 기존 텍스트 위치

    const numberX = textX - 90; // 텍스트 왼쪽에 배치
    const numberY = textY; // 텍스트와 같은 높이

    return (
      <g>
        {/* --- Message number at arrow head --- */}
        {/* 원(circle) 위치 설정 */}
        <circle
          cx={numberX}
          cy={numberY}
          r="10"
          fill="#3b82f6"
        />

        {/* 숫자(text) 위치 설정 (원 위치와 동일하게) */}
        <text
          x={numberX}
          y={numberY + 5}
          fill="white"
          textAnchor="middle"
          className="text-xs fill-white font-semibold"
        >
          {index + 1}
        </text>
        {/* ------------------------------------- */}

        {/* Arrow line */}
        <line
          x1={fromX}
          y1={y}
          x2={toX}
          y2={y}
          stroke="#475569"
          strokeWidth="2"
          strokeDasharray={
            message.type === "async"
              ? "5,5"
              : isReturn
                ? "5,5"
                : undefined
          }
          markerEnd="url(#arrowhead)"
        />
        {/* Message text (글자는 화살표 위로 배치해서 겹침 방지) */}
        <text
          x={(fromX + toX) / 2}
          y={y - 15} // 글자는 y - 15 (위), 숫자는 y + 15 (아래)
          textAnchor="middle"
          className="text-sm fill-slate-700"
        >
          {message.text}
        </text>
      </g>
    );
  };

  return (
    <div className="w-full h-full overflow-auto bg-slate-50 p-8">
      <svg
        width={totalWidth}
        height={totalHeight}
        className="bg-white rounded-lg shadow-sm border border-slate-200"
        ref={svgRef}
      >
        {/* Define arrowhead marker */}
        <defs>
          <marker
            id="arrowhead"
            markerWidth="10"
            markerHeight="10"
            refX="9"
            refY="3"
            orient="auto"
          >
            <polygon points="0 0, 10 3, 0 6" fill="#475569" />
          </marker>
        </defs>

        {/* Participants */}
        {participants.map((participant, index) => {
          const x =
            index * (PARTICIPANT_WIDTH + PARTICIPANT_SPACING);
          const centerX = x + PARTICIPANT_WIDTH / 2;
          const IconComponent = getIconComponent(
            participant.icon,
          );

          return (
            <g key={participant.id}>
              {/* Participant box */}
              <rect
                x={x + 20}
                y={20}
                width={PARTICIPANT_WIDTH}
                height={40}
                rx="4"
                fill="#3b82f6"
                className="transition-colors"
              />
              {/* Icon */}
              {IconComponent && (
                <foreignObject
                  x={x + 28}
                  y={27}
                  width={20}
                  height={20}
                >
                  <IconComponent
                    stroke="white"
                    className="w-5 h-5 text-white"
                  />
                </foreignObject>
              )}
              <text
                x={IconComponent ? centerX + 30 : centerX + 20}
                y={45}
                textAnchor="middle"
                fill="white"
                className="text-sm fill-white font-medium"
              >
                {participant.name}
              </text>

              {/* Lifeline */}
              <line
                x1={centerX + 20}
                y1={LIFELINE_TOP}
                x2={centerX + 20}
                y2={totalHeight - 40}
                stroke="#cbd5e1"
                strokeWidth="2"
                strokeDasharray="5,5"
              />
            </g>
          );
        })}

        {/* Messages */}
        {messages.map((message, index) => {
          const fromX = getParticipantX(message.from) + 20;
          const toX = getParticipantX(message.to) + 20;
          const y = HEADER_HEIGHT + index * MESSAGE_HEIGHT;

          return (
            <g
              key={message.id}
              onClick={() => onMessageClick?.(message)}
              className="cursor-pointer"
            >
              {renderArrow(message, fromX, toX, y, index)}
            </g>
          );
        })}
      </svg>
    </div>
  );
}